package cadc.service.impl;

import cadc.bean.message.STATE;
import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import cadc.entity.TeacherInGroup;
import cadc.mapper.TeacherGroupMapper;
import cadc.mapper.TeacherInGroupMapper;
import cadc.mapper.TeacherMapper;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherGroupServiceImpl extends ServiceImpl<TeacherGroupMapper, TeacherGroup> implements TeacherGroupService {

    @Resource
    private TeacherGroupMapper teacherGroupMapper;
    @Resource
    private TeacherInGroupMapper teacherInGroupMapper;
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Integer add(String groupName, String account, String state) {
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        TeacherGroup teacherGroup = new TeacherGroup( groupName, account, STATE_APPLYING.toString(), sdf.format( new Date() ) );
//        boolean flag = teacherGroupMapper.add( teacherGroup ) > 0;
//        return flag ? teacherGroup.getId() : null;
        return 1;
    }

    @Override
    public Boolean create(String groupName, int teacherId) {
        if (StringUtils.isBlank( groupName )) {
            throw new IllegalArgumentException( "字符串空" );
        }
        TeacherGroup group = new TeacherGroup( groupName );
        group.setState( STATE_APPLYING.toString() );
        group.setCreator( teacherId );
        group.setCreateTime( LocalDateTime.now().toString() );
        boolean flag = group.insert();
        if (flag) {
            return new TeacherInGroup( group.getId(), teacherId, STATE_INVITE_SUCCESS.toString() ).insert();
        }
        return false;
    }


    @Override
    public List<TeacherGroup> findByTeacherId(int id) {
        return teacherGroupMapper.findByTeacherId( id );
    }

    @Override
    public IPage<TeacherGroup> findAll(IPage<TeacherGroup> page) {
        return teacherGroupMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public IPage<TeacherGroup> find(IPage<TeacherGroup> page, String key) {
        QueryWrapper<TeacherGroup> wrapper = new QueryWrapper<>();
        wrapper.like( "id", key ).or()
                .like( "name", key );
        return teacherGroupMapper.selectPage( page, wrapper );
    }

    @Override
    public boolean inviteTeacher(int groupId,String account) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq( "account", account );
        Teacher teacher = teacherMapper.selectOne( wrapper );
        return teacherInGroupMapper.insert( new TeacherInGroup( groupId, teacher.getId(), STATE_INVITING.toString() ) ) > 0;
    }

    @Override
    public List<TeacherGroup> getInviting(int id) {
        return teacherGroupMapper.getInvitingByTeacherId( id );
    }

    @Override
    public boolean updateState(int groupId, String account, String state) {
        return teacherInGroupMapper.updateState( groupId, account, state ) > 0;
    }

    @Override
    public boolean addGroupMember(int groupId, int id) {
        TeacherInGroup teacherInGroup = new TeacherInGroup( groupId, id, STATE_INVITE_SUCCESS.toString() );
        return teacherInGroupMapper.insert( teacherInGroup ) > 0;
    }

    @Override
    public boolean setState(int groupId, String state) {
        return teacherGroupMapper.updateState( groupId, state ) > 0;
    }
}
