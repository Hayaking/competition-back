package cadc.service.impl;

import cadc.bean.message.STATE;
import cadc.entity.TeacherGroup;
import cadc.entity.TeacherInGroup;
import cadc.mapper.TeacherGroupMapper;
import cadc.mapper.TeacherInGroupMapper;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cadc.bean.message.STATE.STATE_INVITING;

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
    public List<TeacherGroup> findByTeacherId(String account) {
        return teacherGroupMapper.findByTeacherId( account );
    }

    @Override
    public boolean inviteTeacher(int groupId,String account) {
        return teacherInGroupMapper.insert( new TeacherInGroup( groupId, account, STATE_INVITING.toString() ) ) > 0;
    }

    @Override
    public boolean updateState(int groupId, String account, String state) {
        return teacherInGroupMapper.updateState( groupId, account, state ) > 0;
    }
}
