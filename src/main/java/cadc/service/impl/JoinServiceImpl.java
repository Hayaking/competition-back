package cadc.service.impl;

import cadc.bean.ENTER_STATE;
import cadc.bean.JOIN_STATE;
import cadc.bean.excel.EnterExcel;
import cadc.bean.excel.EnterExcel2;
import cadc.bean.excel.ExcelModel;
import cadc.bean.holder.EnterHolder;
import cadc.entity.*;
import cadc.mapper.*;
import cadc.service.JoinService;
import cadc.service.ProgressService;
import cadc.util.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JoinInProgressMapper joinInProgressMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private WorksMapper worksMapper;
    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private StudentGroupMapper studentGroupMapper;
    @Autowired
    private ProgressService progressService;

    @Override
    public List<Join> getByGroupId(int groupId) {
        return joinMapper.getSimpleListByGroupId( groupId );
    }

    @Override
    public Page<Join> getListByProgressId(Page<Join> page, int progressId) {
        List<Join> list = joinMapper.getListByProgressId( page, progressId );
        page.setRecords( list );
        return page;
    }

    @Override
    public boolean createJoin(Student student, EnterHolder enterHolder) {
        int joinTypeId = enterHolder.getJoin().getJoinTypeId();
        int competitionId = enterHolder.getJoin().getCompetitionId();
        Competition competition = competitionMapper.selectById( competitionId );
        Boolean isHaveWorks = competition.getIsNeedWorks();
        if (joinTypeId == 1) {
            // 个人赛
            return createSingleJoin( student, isHaveWorks, enterHolder );
        } else {
            // 多人|小组赛
            return createGroupJoin( student, isHaveWorks, enterHolder );
        }
    }

    private boolean createGroupJoin(Student student, Boolean isHaveWorks, EnterHolder enterHolder) {
        StudentGroup group = enterHolder.getGroup();
        // 创建参赛小组
        group.setCreatorId( student.getId() );
        group.setCreateTime( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() ) );
        group.insert();
        // 将创建者 添加进群组
        new StudentInGroup( student.getId(), group.getId(), STATE_INVITE_SUCCESS.toString() ).insert();
        // 邀请组员
        List<String> list = enterHolder.getList();
        for (String item : list) {
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq( "account", item );
            Student stu = studentMapper.selectOne( wrapper );
            new StudentInGroup( stu.getId(), group.getId(), STATE_INVITING.toString() ).insert();
        }
        // 参赛
        Join join = enterHolder.getJoin();
        // 创建作品
        if (isHaveWorks) {
            Works works = enterHolder.getWorks();
            works.setStuGroupId( group.getId() );
            works.setCreatorId( student.getId() );
            works.insert();
            // 设置作品id
            join.setWorksId( works.getId() );
        } else {
            join.setWorksId( null );
        }
        // 设置小组id
        join.setGroupId( group.getId() );
        // 设置指导老师申请状态
        join.setApplyState( STATE_APPLYING.toString() );
        join.setApplyState2( STATE_APPLYING.toString() );
        join.setJoinTypeId( 1 );
        join.setCreatorId( student.getId() );
        join.setCreateTime( new Date() );
        join.insert();
        List<Progress> progressList = progressService.getByCompetitionId( join.getCompetitionId() );
        return new JoinInProgress() {{
            setJoinId( join.getId() );
            setProgressId( progressList.get( 0 ).getId() );
            // 得奖状态
            setPriceState( false );
            // 审核比赛结果状态
            setReviewState( false );
            // 晋级状态
            setPromotionState( false );
            // 报名状态
            setEnterState( false );
        }}.insert();
    }

    private boolean createSingleJoin(Student student, Boolean isHaveWorks, EnterHolder enterHolder) {
        Join join = enterHolder.getJoin();
        if (isHaveWorks) {
            Works works = enterHolder.getWorks();
            works.setCreatorId( student.getId() );
            works.insert();
            join.setWorksId( works.getId() );
        } else {
            join.setWorksId( null );
        }
        join.setGroupId( null );
        join.setJoinTypeId( 2 );
        if (join.getTeacher1() != null) {
            join.setApplyState( STATE_APPLYING.toString() );
        }
        if (join.getTeacher2() != null) {
            join.setApplyState2(  STATE_APPLYING.toString() );
        }
        join.setCreatorId( student.getId() );
        join.setCreateTime( new Date() );
        join.insert();
        List<Progress> progressList = progressService.getByCompetitionId( join.getCompetitionId() );
        return new JoinInProgress() {{
            setJoinId( join.getId() );
            setProgressId( progressList.get( 0 ).getId() );
            // 得奖状态
            setPriceState( false );
            // 审核比赛结果状态
            setReviewState( false );
            // 晋级状态
            setPromotionState( false );
            // 报名状态
            setEnterState( false );
        }}.insert();
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
    public IPage<Join> getEnterList(Page<Join> page, int competitionId, int progressId) {
        List<Join> list = joinMapper.getEnterList( page, competitionId, progressId );
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
    public String generateEnterListExcel(int competitionId, int progressId) {
        Competition competition = competitionMapper.getById( competitionId );
        // 竞赛是需要参赛作品
        boolean isHaveWorks = competition.getIsNeedWorks();

        // 参赛类型
        Progress progress = progressService.getById( progressId );
        Boolean isSingle = progress.getIsSingle();
//        int joinTypeId = competition.getJoinTypeId();
        List<Join> joinList = joinMapper.getListByCompetitionIdProgressId( competitionId, progressId );
        List<ExcelModel> data = new LinkedList<>();
        if (!isSingle) {
            //小组赛
            joinList.spliterator().forEachRemaining( item -> {
                StringBuilder members = new StringBuilder();
                item.getWorks()
                        .getStudentGroup()
                        .getMembers()
                        .spliterator()
                        .forEachRemaining( member ->
                                members.append( member.getStudent().getStuName() + "," )
                        );
                data.add( new EnterExcel(
                        item.getId(),
                        item.getWorks().getWorksName(),
                        members.toString(),
                        "" )
                );
            } );
        }
        else {
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

}
