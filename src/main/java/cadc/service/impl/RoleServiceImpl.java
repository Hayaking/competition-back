package cadc.service.impl;

import cadc.entity.Role;
import cadc.mapper.RoleMapper;
import cadc.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author haya
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findStudent(String account) {
        return roleMapper.findStudentRoles( account );
    }

    @Override
    public List<Role> findTeacher(String account) {
        return roleMapper.findTeacherRoles( account );
    }
}
