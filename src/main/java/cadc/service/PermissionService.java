package cadc.service;

import cadc.entity.Permission;
import cadc.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 根据角色id 找到角色拥有的权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionList(int roleId);

    boolean addPermissionToRole(RolePermission rolePermission);

    boolean deletePermissionToRole(RolePermission rolePermission);
}
