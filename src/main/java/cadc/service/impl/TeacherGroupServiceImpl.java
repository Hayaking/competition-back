package cadc.service.impl;

import cadc.bean.message.STATE;
import cadc.entity.TeacherGroup;
import cadc.entity.TeacherInGroup;
import cadc.mapper.TeacherGroupMapper;
import cadc.mapper.TeacherInGroupMapper;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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

    @Override
    public Integer add(String groupName, String account, String state) {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        TeacherGroup teacherGroup = new TeacherGroup( groupName, account, STATE_APPLYING.toString(), sdf.format( new Date() ) );
        boolean flag = teacherGroupMapper.add( teacherGroup ) > 0;
        return flag ? teacherGroup.getId() : null;
    }

    @Override
    public List<TeacherGroup> findByTeacherId(String account) {
        return teacherGroupMapper.findByTeacherId( account );
    }

    @Override
    public IPage<TeacherGroup> findAll(IPage<TeacherGroup> page) {
        return teacherGroupMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public boolean inviteTeacher(int groupId,String account) {
        return teacherInGroupMapper.insert( new TeacherInGroup( groupId, account, STATE_INVITING.toString() ) ) > 0;
    }

    @Override
    public boolean updateState(int groupId, String account, String state) {
        return teacherInGroupMapper.updateState( groupId, account, state ) > 0;
    }

    @Override
    public boolean addGroupMember(int groupId, String account) {
        TeacherInGroup teacherInGroup = new TeacherInGroup(groupId, account,STATE_INVITE_SUCCESS.toString());
        return teacherInGroupMapper.insert( teacherInGroup ) > 0;
    }

    @Override
    public boolean setState(int groupId, String state) {
        return teacherGroupMapper.updateState( groupId, state ) > 0;
    }
}
