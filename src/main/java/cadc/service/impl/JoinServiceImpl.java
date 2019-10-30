package cadc.service.impl;

import cadc.bean.excel.EnterExcel;
import cadc.bean.excel.EnterExcel2;
import cadc.bean.excel.ExcelModel;
import cadc.entity.*;
import cadc.mapper.*;
import cadc.service.JoinService;
import cadc.util.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Log4j2
public class JoinServiceImpl extends ServiceImpl<JoinMapper, Join> implements JoinService {
    @Resource
    private JoinMapper joinMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private WorksMapper worksMapper;
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private StudentGroupMapper studentGroupMapper;

    @Override
    public boolean createGroupJoin(Student student, StudentGroup group, List<String> list, Works works, Join join) {
        if (group == null || works == null || list == null || join == null) {
            throw new NullPointerException();
        }
        // 创建参赛小组
        group.setCreatorId( student.getId() );
        group.setCreateTime( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() ) );
        group.insert();
        if (group.getId() == 0) {
            throw new NullPointerException();
        }
        // 将创建者 添加进群组
        new StudentInGroup( student.getId(), group.getId(), STATE_INVITE_SUCCESS.toString() ).insert();
        // 邀请组员
        for (String item : list) {
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq( "account", item );
            Student stu = studentMapper.selectOne( wrapper );
            new StudentInGroup( stu.getId(), group.getId(), STATE_INVITING.toString() ).insert();
        }
        // 创建作品
        works.setStuGroupId( group.getId() );
        works.insert();
        // 参赛
        join.setGroupId( group.getId() );
        join.setWorksId( works.getId() );
        join.setApplyState( STATE_APPLYING.toString() );
        join.setEnterState( STATE_APPLYING.toString() );
        join.setJoinState( STATE_NOT_START.toString() );
        join.setJoinTypeId( 1 );
        join.setCreatorId( student.getId() );
        join.setCreateTime( new Date() );
        join.insert();
        return true;
    }

    @Override
    public List<Join> getByGroupId(int groupId) {
        QueryWrapper<Join> wrapper = new QueryWrapper<>();
        wrapper.eq( "group_id", groupId );
        return joinMapper.selectList( wrapper );
    }

    @Override
    public IPage<Join> getByStudentId(Page<Join> page, int id) {
        List<Join> list = joinMapper.getJoinListByStudentId( page, id );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Join> getByLead(Page<Join> page, int teacherId) {
        List<Join> list = joinMapper.getListByTeacherId( page, teacherId );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Join> searchByLead(Page<Join> page, String key, int teacherId) {
        QueryWrapper<Works> worksQueryWrapper = new QueryWrapper<>();
        QueryWrapper<Competition> competitionQueryWrapper = new QueryWrapper<>();
        QueryWrapper<StudentGroup> studentGroupQueryWrapper = new QueryWrapper<>();
        Competition competition = competitionMapper.selectOne( competitionQueryWrapper.eq( "name", key ) );
        Works works = worksMapper.selectOne( worksQueryWrapper.eq( "works_name", key ) );
        StudentGroup group = studentGroupMapper.selectOne( studentGroupQueryWrapper.eq( "name", key ) );
        List<Join> list = joinMapper.searchListByOtherId( page,
                works == null ? 0 : works.getId(),
                competition == null ? 0 : competition.getId(),
                group == null ? 0 : group.getId(),
                teacherId );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Join> getByCompetitionId(Page<Join> page, int competitionId) {
        List<Join> list = joinMapper.getListByCompetitionId( page, competitionId );
        page.setRecords( list );
        return page;
    }

    @Override
    public Boolean setApplyState(Boolean flag, int joinId, int teacherId) {
        Join join = joinMapper.selectById( joinId );
        UpdateWrapper<Join> wrapper = new UpdateWrapper<>();
        wrapper.set( "apply_state", flag ? STATE_AGREE.toString() : STATE_REFUSE.toString() );
        return joinMapper.update( join, wrapper ) > 0;
    }

    @Override
    public String generateEnterListExcel(int competitionId) {
        Competition competition = competitionMapper.getById( competitionId );
        // 竞赛是需要参赛作品
        boolean isHaveWorks = competition.getIsHaveWorks();
        // 参赛类型
        int joinTypeId = competition.getJoinTypeId();
        List<Join> joinList = joinMapper.getListByCompetitionId( competitionId );
        List<ExcelModel> data = new LinkedList<>();
        if (joinTypeId == 1) {
            //小组赛
            joinList.spliterator().forEachRemaining( item -> {
                StringBuilder members = new StringBuilder();
                item.getWorks().getStudentGroup().getMembers().spliterator().forEachRemaining( member -> {
                    members.append( member.getStudent().getStuName() + "," );
                } );
                data.add( new EnterExcel( item.getId(), item.getWorks().getWorksName(), members.toString(), "" ) );
            } );
        } else if (joinTypeId == 2) {
            //单人赛
            joinList.spliterator().forEachRemaining( item -> {
                data.add( isHaveWorks
                        ? EnterExcel.builder()
                            .index( item.getId() )
                            .worksName( item.getWorks().getWorksName() )
                            .member( item.getCreator().getStuName() )
                            .lead1( "" ).build()
                        : EnterExcel2.builder()
                            .index( item.getId() )
                            .member( item.getCreator().getStuName() )
                            .lead1( "" ).build() );
            } );
        }
        String fileName = LocalDate.now().toString() + competitionId;
        ExcelUtils.generateExcel( fileName, "", data, isHaveWorks ? EnterExcel.class : EnterExcel2.class );
        return fileName;
    }

    @Override
    public boolean setEnterState(Boolean flag, int joinId) {
        Join join = joinMapper.selectById( joinId );
        UpdateWrapper<Join> wrapper = new UpdateWrapper<>();
        wrapper.set( "enter_state", flag ? STATE_AGREE.toString() : STATE_REFUSE.toString() );
        return joinMapper.update( join, wrapper ) > 0;
    }

    @Override
    public boolean createSingleJoin(Student student, Works works, Join join) {
        works.setCreatorId( student.getId() );
        works.insert();

        join.setJoinTypeId( 2 );
        join.setWorksId( works.getId() );
        join.setApplyState( STATE_APPLYING.toString() );
        join.setEnterState( STATE_APPLYING.toString() );
        join.setJoinState( STATE_NOT_START.toString() );
        join.setCreatorId( student.getId() );
        join.setCreateTime( new Date() );
        return join.insert();
    }
}
