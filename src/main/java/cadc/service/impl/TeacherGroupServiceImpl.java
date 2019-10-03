package cadc.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    public Boolean create(TeacherGroup group, int teacherId) {
        if (group == null) {
            throw new IllegalArgumentException( "空" );
        }
        if (StringUtils.isBlank( group.getName() )) {
            throw new IllegalArgumentException( "字符串空" );
        }
        // 创建小组
        group.setState( STATE_APPLYING.toString() );
        group.setCreatorId( teacherId );
        group.setCreateTime( LocalDateTime.now().toString() );
        boolean flag = group.insert();
        // 将创建人添加进入小组
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
    public IPage<TeacherGroup> findPageByTeacherId(IPage<TeacherGroup> page, int id) {
        List<TeacherGroup> list = teacherGroupMapper.findByTeacherIdByPage( page, id );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<TeacherGroup> findAll(IPage<TeacherGroup> page) {
        List<TeacherGroup> list = teacherGroupMapper.getAllByPage( page );
        page.setRecords( list );
        return page;
    }

    @Override
    public IPage<TeacherGroup> find(IPage<TeacherGroup> page, String key) {
        QueryWrapper<TeacherGroup> wrapper = new QueryWrapper<>();
        wrapper.like( "id", key ).or()
                .like( "name", key );
        return teacherGroupMapper.selectPage( page, wrapper );
    }

    @Override
    public boolean inviteTeacher(int groupId, int teacherId) {
        return teacherInGroupMapper
                .insert( new TeacherInGroup( groupId, teacherId, STATE_INVITING.toString() ) ) > 0;
    }

    @Override
    public List<TeacherGroup> getInviting(int id) {
        return teacherGroupMapper.getInvitingByTeacherId( id );
    }

    @Override
    public boolean updateState(int groupId,  int teacherId, String state) {
        return teacherInGroupMapper.updateState( groupId, teacherId, state ) > 0;
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

    @Override
    public boolean exit(int groupId, int teacherId) {
        TeacherGroup teacherGroup = teacherGroupMapper.selectById( groupId );
        if (teacherId == teacherGroup.getCreatorId()) {
            return false;
        }
        HashMap<String, Object> map = new HashMap<>( 2 );
        map.put( "group_id", groupId );
        map.put( "teacher_id", teacherId );
        return teacherInGroupMapper.deleteByMap( map ) > 0;
    }
}
