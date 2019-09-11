package cadc.service.impl;

import cadc.entity.Role;
import cadc.entity.RoleStudent;
import cadc.entity.RoleTeacher;
import cadc.mapper.RoleMapper;
import cadc.mapper.RoleStudentMapper;
import cadc.mapper.RoleTeacherMapper;
import cadc.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleStudentMapper roleStudentMapper;
    @Resource
    private RoleTeacherMapper roleTeacherMapper;

    @Override
    public List<Role> findStudent(int id) {
        return roleMapper.findStudentRoles( id );
    }

    @Override
    public List<Role> findTeacher(int id) {
        return roleMapper.findTeacherRoles( id );
    }

    @Override
    public boolean deleteStudent(int id, int roleId) {
        UpdateWrapper<RoleStudent> wrapper = new UpdateWrapper<>();
        wrapper.eq( "stu_id", id ).eq( "role_id", roleId );
        return roleStudentMapper.delete( wrapper ) > 0;
    }

    @Override
    public boolean addStudent(int id, int roleId) {
        RoleStudent roleStudent = new RoleStudent( id, roleId );
        return roleStudentMapper.insert( roleStudent ) > 0;
    }

    @Override
    public boolean addTeacher(int id, int roleId) {
        RoleTeacher roleTeacher = new RoleTeacher( id, roleId );
        return roleTeacherMapper.insert( roleTeacher ) > 0;
    }

    @Override
    public boolean deleteTeacher(int id, int roleId) {
        UpdateWrapper<RoleTeacher> wrapper = new UpdateWrapper<>();
        wrapper.eq( "teacher_id", id ).eq( "role_id", roleId );
        return roleTeacherMapper.delete( wrapper ) > 0;
    }
}
