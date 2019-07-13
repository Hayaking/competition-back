package cadc.service;

import cadc.entity.Permission;
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
}
