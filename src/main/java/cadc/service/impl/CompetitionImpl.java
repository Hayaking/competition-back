package cadc.service.impl;

import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CompetitionApplyPolicy;
import cadc.entity.*;
import cadc.mapper.CompetitionMapper;
import cadc.mapper.JoinMapper;
import cadc.mapper.ProgressMapper;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.CompetitionService;
import cadc.service.TeacherGroupLogService;
import cadc.util.WordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static cadc.bean.PROGRESS_STATE.NO_START;
import static cadc.bean.message.STATE.STATE_AGREE;

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
    @Resource
    private TeacherGroupMapper teacherGroupMapper;
    @Autowired
    private TeacherGroupLogService teacherGroupLogService;

    @Override
    public boolean createCompetition(Teacher teacher, Competition competition, List<Progress> progresses, List<Budget> budgets) {
        //1. 判断是不是组长
        int groupId = competition.getTeacherGroupId();
        TeacherGroup teacherGroup = teacherGroupMapper.selectById( groupId );
        int creatorId = teacherGroup.getCreatorId();
        int teacherId = teacher.getId();
        if (creatorId != teacherId) {
            return false;
        }
        // 2.创建竞赛
        competition.setState( 0 );
        competition.setCreatorId( teacherId );
        competition.setCreateTime( new Date() );
        competitionMapper.insert( competition );
        // 3.创建比赛阶段
        AtomicInteger index = new AtomicInteger();
        progresses.forEach( item -> {
            item.setCompetitionId( competition.getId() );
            item.setEnterState( NO_START.getCode() );
            item.setStartState( NO_START.getCode() );
            item.setIsScanEnterState( true );
            item.setIsScanStartState( true );
            progressMapper.insert( item );
            // 创建预算
            Budget budget = budgets.get( index.getAndIncrement() );
            budget.setProgressId( item.getId() );
            budget.insert();
        } );
        // 4.生成word
        this.getWord( competition.getId() );
        // 5.日志
        teacherGroupLogService.add( new TeacherGroupLog() {{
            setGroupId( groupId );
            setOperatorId( teacherId );
            setCreateTime( new Date() );
            setAction( teacher.getTeacherName() + ",创建了" + competition.getName() );
        }} );
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
    public boolean setState(int id, int state) {
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

    @Async
    @Override
    public FileInputStream getWord(int competitionId) {
        Competition competition = competitionMapper.getWithProgressListById( competitionId );
        Map<String, Object> props = WordUtils.competitionMapToWord( competition );
        Configure config = Configure
                .newBuilder()
                .customPolicy( "progress", new CompetitionApplyPolicy() )
                .build();
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String fileName = competition.getName() + ".docx";
        FileInputStream fis = null;
        try {
            XWPFTemplate.compile( root + "template/competition.docx", config )
                    .render( props )
                    .writeToFile( root + fileName );
            fis = new FileInputStream( new File( root + fileName ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

    @Override
    public FileInputStream getBudgetWord2(int competitionId) {
        Competition competition = competitionMapper.getWithBudgetListById( competitionId );
        Map<String, Object> props = new HashMap<String, Object>() {{
            put( "name", competition.getName() );
            put( "budget", competition.getProgressList() );
        }};
        String fileName = competitionId + "_" + competition.getName() + "_budget" + ".doc";
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        Configure config = Configure
                .newBuilder()
                .customPolicy( "budget", new BudgetPolicy() )
                .build();
        FileInputStream fis = null;
        try {
            XWPFTemplate.compile( root + "template/budget.docx", config )
                    .render( props )
                    .writeToFile( root + fileName );
            fis = new FileInputStream( new File( root + fileName ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

    @Override
    public FileInputStream getBudgetWord(int competitionId) {
        Competition competition = competitionMapper.getWithBudgetListById( competitionId );
        String fileName = competitionId + "_" + competition.getName() + "_budget" + ".docx";
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        FileInputStream fis = null;
        Map<String, Object> props = new HashMap<String, Object>() {{
            put( "name", competition.getName() );
            put( "budget", competition.getProgressList() );
        }};
        Configure config = Configure
                .newBuilder()
                .customPolicy( "budget", new BudgetPolicy() )
                .build();
        try {
            XWPFTemplate.compile( root + "template/budget.docx", config )
                    .render( props )
                    .writeToFile( root + fileName );
            fis = new FileInputStream( new File( root + fileName ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

//    @Override
//    public FileInputStream getBudgetWord(int competitionId) {
//        Competition competition = competitionMapper.getWithBudgetListById( competitionId );
//        String fileName = competitionId + "_" + competition.getName() + "_budget" + ".doc";
//        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
//        FileInputStream fis = null;
//        try {
//            Map<String, Object> prop = WordUtils.budgetMapToWord( competition );
//            fis = WordUtils.generateBugetWord( root, fileName, prop );
//        } catch (TemplateException | IOException e) {
//            e.printStackTrace();
//        }
//        return fis;
//    }

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

    @Override
    public Competition getWithProgressById(int id) {
        return competitionMapper.getWithProgressListById( id );
    }
}
