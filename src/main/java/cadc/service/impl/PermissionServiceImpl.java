package cadc.service.impl;

import cadc.entity.Permission;
import cadc.entity.PermissionMenu;
import cadc.entity.RolePermission;
import cadc.mapper.PermissionMapper;
import cadc.mapper.PermissionMenuMapper;
import cadc.mapper.RolePermissionMapper;
import cadc.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private PermissionMenuMapper permissionMenuMapper;

    @Override

    public List<Permission> findPermissionList(int roleId) {
        return permissionMapper.findList( roleId );
    }

    @Override
    public boolean addPermissionToRole(RolePermission rolePermission) {
        return rolePermissionMapper.insert( rolePermission ) > 0;
    }

    @Override
    public boolean deletePermissionToRole(RolePermission rolePermission) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "role_id", rolePermission.getRoleId() ).eq( "permission_id", rolePermission.getPermissionId() );
        rolePermission = rolePermissionMapper.selectOne( queryWrapper );
        UpdateWrapper<RolePermission> wrapper = new UpdateWrapper<>();
        wrapper.eq( "id", rolePermission.getId() );
        return rolePermissionMapper.delete( wrapper ) > 0;
    }

    @Override
    public int getIdByMenuId(int menuId) {
        return permissionMenuMapper.selectOne( new QueryWrapper<PermissionMenu>().eq( "menu_id", menuId ) ).getPermissionId();
    }

    @Override
    public boolean savePermissionMenu(PermissionMenu permissionMenu) {
        return permissionMenuMapper.insert( permissionMenu ) > 0;
    }
}
