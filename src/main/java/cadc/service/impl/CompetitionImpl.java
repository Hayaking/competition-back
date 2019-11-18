package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.CompetitionMapper;
import cadc.mapper.JoinMapper;
import cadc.mapper.ProgressMapper;
import cadc.service.CompetitionService;
import cadc.util.WordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Service
@Log4j2
@Transactional(rollbackFor = Exception.class)
public class CompetitionImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private JoinMapper joinMapper;
    @Resource
    private ProgressMapper progressMapper;

    @Override
    public boolean createCompetition(Teacher teacher, Competition competition, List<Progress> progresses, List<Budget> budgets) {
        competition.setState( 0 );
        competition.setCreatorId( teacher.getId() );
        competition.setCreateTime( new Date() );
        competitionMapper.insert( competition );
        AtomicInteger index = new AtomicInteger();
        progresses.forEach( item -> {
            item.setCompetitionId( competition.getId() );
            item.setEnterState( STATE_NOT_START.toString() );
            item.setStartState( STATE_NOT_START.toString() );
            item.setIsScanEnterState( true );
            item.setIsScanStartState( true );
            progressMapper.insert( item );
            Budget budget = budgets.get( index.getAndIncrement() );
            budget.setProgressId( item.getId() );
            budget.insert();
        } );
        this.getWord( competition.getId() );
        return true;
    }

    @Override
    public Integer add(Competition competition) {
        int insert = competitionMapper.insert( competition );
        return competition.getId();
    }

    @Override
    public boolean deleteById(int id) {
        //先删除join里引用外键的内容
        UpdateWrapper<Join> joinWrapper = new UpdateWrapper<>();
        joinWrapper.eq( "competition_id", id );
        joinMapper.delete( joinWrapper );
        return competitionMapper.deleteById( id ) > 0;
    }

    @Override
    public IPage<Competition> findByGroupId(Page<Competition> page, int groupId) {
        List<Competition> list = competitionMapper.getWithProgressListByTeacherGroupId( page, groupId );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Competition> findAll(Page<Competition> page) {
        List<Competition> list = competitionMapper.getAllByPage( page );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Competition> find(IPage<Competition> page, String key) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.like( "id", key ).or()
                .like( "name", key );
        return competitionMapper.selectPage( page, wrapper );
    }

    @Override
    public IPage<Competition> findPassByKey(IPage<Competition> page, String key) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "state", STATE_AGREE.toString() )
                .like( "id", key ).or()
                .like( "name", key );
        return competitionMapper.selectPage( page, wrapper );
    }

    @Override
    public IPage<Competition> findPassAll(Page<Competition> page) {
        List<Competition> list = competitionMapper.getPassList( page );
        page.setRecords( list );
        return page;
    }


    @Override
    public boolean setState(int id, String state) {
        return competitionMapper.updateState( id, state ) > 0;
    }

    @Override
    public boolean setStartState(int id, String state) {
        return competitionMapper.updateStartState( id, state ) > 0;
    }

    @Override
    public List<Competition> getStartNoEnd() {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
//        wrapper.eq( "start_state", STATE_NOT_START.toString() ).or()
//                .eq( "start_state", STATE_HAD_START.toString() );
        return competitionMapper.selectList( wrapper );
    }

    @Override
    public List<Competition> get5ByType(int typeId) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        // 查询已开始的
        wrapper.eq( "state", 1 )
                .last( "LIMIT 5" );
        List<Competition> list = competitionMapper.selectList( wrapper );
        return list;
    }

    @Override
    public List<Competition> getEnterNoEnd() {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
//        wrapper.eq( "enter_state", STATE_NOT_START.toString() ).or()
//                .eq( "enter_state", STATE_HAD_START.toString() );
        return competitionMapper.selectList( wrapper );
    }

    @Override
    public boolean setEnterState(int id, String state) {
        return competitionMapper.updateEnterState( id, state ) > 0;
    }

    @Override
    public FileInputStream getWord(int competitionId) {
        Competition competition = competitionMapper.getWithProgressListById( competitionId );
        String fileName = competitionId + "_" + competition.getName() + ".doc";
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        FileInputStream fis = null;
        try {
            Map<String, Object> prop = WordUtils.competitionMapToWord( competition );
            fis = WordUtils.generateWord( root, fileName, prop );
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

    @Override
    public FileInputStream getBudgetWord(int competitionId) {
        Competition competition = competitionMapper.getWithBudgetListById( competitionId );
        String fileName = competitionId + "_" + competition.getName() + "_budget" + ".doc";
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        FileInputStream fis = null;
        try {
            Map<String, Object> prop = WordUtils.budgetMapToWord( competition );
            fis = WordUtils.generateBugetWord( root, fileName, prop );
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
        return fis;

    }

    @Override
    public List<Competition> findByGroupId(int groupId) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "teacher_group_id", groupId );
        List<Competition> list = competitionMapper.selectList( wrapper );
        if (list.size() <= 5) {
            return list;
        }
        return list.subList( 0, 5 );
    }
}
