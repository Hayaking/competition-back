package cadc.service.impl;

import cadc.bean.Enter;
import cadc.bean.excel.EnterExcel;
import cadc.entity.*;
import cadc.mapper.JoinMapper;
import cadc.mapper.StudentMapper;
import cadc.mapper.WorksMapper;
import cadc.service.JoinService;
import cadc.util.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class JoinServiceImpl extends ServiceImpl<JoinMapper, Join> implements JoinService {
    @Resource
    private JoinMapper joinMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private WorksMapper worksMapper;

    @Override
    public boolean create(Student student, StudentGroup group, List<String> list, Works works, Join join) {
        if (group == null || works == null || list == null || join == null) {
            throw new NullPointerException();
        }
        // 创建参赛小组
        group.setCreator( student.getId() );
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
//        works.insert();
        worksMapper.insert( works );
        // 参赛
        join.setWorksId( works.getId() );
        join.setApplyState( STATE_APPLYING.toString() );
        join.setEnterState( STATE_APPLYING.toString() );
        join.setJoinState( STATE_NOT_START.toString() );
        join.insert();
        return true;
    }

    @Override
    public IPage<Join> getByStudentAccount(Page<Join> page, String account) {
        List<Join> list = joinMapper.getListByStudentAccount( account );
        System.out.println( list );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<Join> getByLead(Page<Join> page, int teacherId) {
        QueryWrapper<Join> wrapper = new QueryWrapper<>();
        wrapper.eq( "teacher_id1", teacherId ).or()
                .eq( "teacher_id2", teacherId );
        return joinMapper.selectPage( page, wrapper );
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
    public boolean generateEnterListExcel(int competitionId) {
        List<Join> list = joinMapper.getListByCompetitionId( competitionId );
        List<EnterExcel> data = new LinkedList<>();
        list.spliterator().forEachRemaining( item -> {
//            List<String> members = new LinkedList<>();
            StringBuilder members = new StringBuilder();

            item.getWorks().getStudentGroup().getMembers().spliterator().forEachRemaining( member -> {
                members.append( member.getStudent().getStuName() + "," );
            } );
            data.add( new EnterExcel( item.getId(), item.getWorks().getWorksName(), members.toString(), "" ) );

        } );
        ExcelUtils.generateExcel( LocalDate.now().toString() + competitionId, "", data, EnterExcel.class );
        return true;
    }
}
