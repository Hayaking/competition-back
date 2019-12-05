package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.TeacherGroupMapper;
import cadc.mapper.TeacherInGroupMapper;
import cadc.mapper.TeacherMapper;
import cadc.service.TeacherGroupLogService;
import cadc.service.TeacherGroupService;
import cadc.websocket.WebSocketHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Autowired
    private TeacherGroupLogService teacherGroupLogService;
    @Autowired
    private WebSocketHandler webSocketHandler;

    @Override
    public Boolean create(TeacherGroup group, int teacherId) {
        // 1.检查参数
        if (group == null || StringUtils.isBlank( group.getName() )) {
            return false;
        }
        Teacher teacher = teacherMapper.selectById( teacherId );
        if (teacher == null) {
            return false;
        }
        // 2.创建小组
        group.setState( STATE_APPLYING.toString() );
        group.setCreatorId( teacherId );
        group.setCreateTime( LocalDateTime.now().toString() );
        boolean flag = group.insert();
        // 3.将创建人添加进入小组
        if (flag) {
            new TeacherInGroup( group.getId(), teacherId, STATE_INVITE_SUCCESS.toString() ).insert();
            //4.日志
            teacherGroupLogService.add( new TeacherGroupLog() {{
                setGroupId( group.getId() );
                setCreateTime( new Date() );
                setOperatorId( teacherId );
                setAction( teacher.getTeacherName() + "创建工作组：" + group.getName() );
            }} );
            return true;
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
    public boolean inviteTeacher(Teacher leader,int groupId, int teacherId) {
        log.warn( "邀请组员" );
        Teacher teacher = teacherMapper.selectById( teacherId );
        TeacherGroup group = teacherGroupMapper.selectById( groupId );
        // 1.添加目标进工作组 设置状态为邀请中
        boolean res = teacherInGroupMapper.insert( new TeacherInGroup( groupId, teacherId, STATE_INVITING.toString() ) ) > 0;
        if (res) {
            // 3.判断在线不
            //在线发送websocket
            if (webSocketHandler.isOnLine( teacherId )) {
                log.warn( "在线" );
                webSocketHandler.send( "invite", new Message() {{
                    setIsRead( false );
                    setIsFromStudent( false );
                    setIsToStudent( false );
                    setCreateTime( new Date() );
                    setTo( String.valueOf( teacherId ) );
                    setFrom( String.valueOf( leader.getId() ) );
                    setExtra( String.valueOf( groupId ) );
                    setBody( teacher.getTeacherName() + "邀请你加入" + group.getName() );
                }} );
            } else {
                log.warn( "不在线" );
                // 不在线则发送邮件
                // 添加到未读消息
            }
            // 2.日志
            teacherGroupLogService.add( new TeacherGroupLog(){{
                setOperatorId( leader.getId() );
                setCreateTime( new Date() );
                setGroupId( groupId );
                setAction( leader.getTeacherName() + ",邀请" + teacher.getTeacherName() + "加入工作组" );
            }} );
        }
        return res;
    }

    @Override
    public List<TeacherGroup> getInviting(int id) {
        return teacherGroupMapper.getInvitingByTeacherId( id );
    }

    @Override
    public boolean updateState(int groupId, int teacherId, String state) {
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

    @Override
    public boolean delete(int groupId, int teacherId) {
        TeacherGroup group = teacherGroupMapper.selectById( groupId );
        int creatorId = group.getCreatorId();
        boolean flag = teacherId == creatorId;
        if (flag) {
            UpdateWrapper<TeacherInGroup> wrapper = new UpdateWrapper<>();
            wrapper.eq( "group_id", groupId );
            teacherInGroupMapper.delete( wrapper );
            teacherGroupMapper.deleteById( groupId );
            return true;
        }
        return false;
    }
}
