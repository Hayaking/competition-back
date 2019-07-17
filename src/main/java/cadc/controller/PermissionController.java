package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Permission;
import cadc.entity.PermissionMenu;
import cadc.entity.RolePermission;
import cadc.service.PermissionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取指定角色拥有的权限
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/permission/{roleId}", method = RequestMethod.GET)
    public Object getByRoleId(@PathVariable int roleId) {
        List<Permission> list = permissionService.findPermissionList( roleId );
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 获取所有权限
     *
     * @return
     */
    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public Object getAll() {
        List<Permission> list = permissionService.list();
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 给指定角色添加权限
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "/permission/{roleId}/{permissionId}", method = RequestMethod.POST)
    public Object addPermissionToRole(@PathVariable int roleId, @PathVariable int permissionId) {
        RolePermission rolePermission = new RolePermission( roleId, permissionId );
        boolean flag = permissionService.addPermissionToRole( rolePermission );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 删除指定的权限
     *
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.DELETE)
    public Object deletePermission(@PathVariable int permissionId) {
        boolean flag = permissionService.removeById( permissionId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 删除指定角色拥有的权限
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "/permission/{roleId}/{permissionId}", method = RequestMethod.DELETE)
    public Object deletePermissionToRole(@PathVariable int roleId, @PathVariable int permissionId) {
        RolePermission rolePermission = new RolePermission( roleId, permissionId );
        boolean flag = permissionService.deletePermissionToRole( rolePermission );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 添加权限
     *
     * @param permissionName
     * @return
     */
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public Object addPermission(String permissionName) {
        Permission permission = new Permission();
        permission.setPermissionName( permissionName );
        boolean flag = permissionService.save( permission );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 查询指定菜单对应的权限id
     *
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/permission/menu/{menuId}", method = RequestMethod.GET)
    public Object getIdByMenuId(@PathVariable int menuId) {
        int id = permissionService.getIdByMenuId( menuId );
        return MessageFactory.message( SUCCESS, id );
    }

    /**
     * 添加/更新 权限与菜单的关系
     * @param menuId
     * @param permissionId
     * @return
     */
    @RequestMapping(value = "/permission/menu/{menuId}/{permissionId}", method = RequestMethod.POST)
    public Object savePermissionMenu(@PathVariable int menuId, @PathVariable int permissionId) {
        PermissionMenu permissionMenu = new PermissionMenu(  permissionId ,menuId);
        boolean flag = permissionService.savePermissionMenu( permissionMenu );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }
}
