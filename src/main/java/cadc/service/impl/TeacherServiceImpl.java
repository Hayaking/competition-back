package cadc.service.impl;

import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.mapper.TeacherMapper;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Teacher find(String account, String password) {
        return teacherMapper.find( account, password );
    }

    @Override
    public List<Teacher> getByGroupId(int groupId) {
        return teacherMapper.getByGroupId( groupId );
    }

    @Override
    public List<Teacher> getInvitingByGroupId(int groupId) {
        return teacherMapper.getInvitingByGroupId( groupId );
    }

    @Override
    public IPage<Teacher> findAll(IPage<Teacher> page) {
        return teacherMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public IPage<Teacher> find(IPage<Teacher> page, String key) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.like( "account", key ).or()
                .like( "teacher_name", key );
        return teacherMapper.selectPage( page, wrapper );
    }

    @Override
    public List<Teacher> getByRole(int roleId) {
        return teacherMapper.getByRoleId( roleId );
    }

}
