package cadc.service.impl;

import cadc.entity.Menu;
import cadc.entity.Meta;
import cadc.entity.Permission;
import cadc.entity.Role;
import cadc.mapper.*;
import cadc.service.MenuService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haya
 */
@Log4j2
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private MetaMapper metaMapper;
    @Resource
    private MenuChildMapper menuChildMapper;

    @Override
    public List<Menu> getMenu(String account, String type) {
        //获取角色
        List<Role> roleList = null;
        if ("student".equals( type )) {
            roleList = roleMapper.findStudentRoles( account );
        } else if ("teacher".equals( type )) {
            roleList = roleMapper.findTeacherRoles( account );
        } else {
            return null;
        }
        List<Permission> perList = new LinkedList<>();

        List<Menu> res = new LinkedList<>();
        log.info( "获取权限" );
        //获取权限
        for (Role item : roleList) {
            perList.addAll( permissionMapper.findList( item.getId() ) );
        }
        log.info( perList);
        log.info( "获取菜单" );
        //获取菜单
        for (Permission item : perList) {
            Menu menu = menuMapper.findMainMenu( item.getId() );
            if (null == menu) {
                continue;
            }
            menu = buildMenu( menu );
            Meta meta = null;
            if (menu.getMetaId() != null) {
                meta = metaMapper.findMeta( menu.getMetaId() );
            }
            menu.setMeta( meta );
            res.add( menu );
        }
        return res;
    }

    public Menu buildMenu(Menu root) {
        List<Menu> children = menuChildMapper.find( root.getId() );
        if (children != null) {
            for (Menu item : children) {
                Menu menu = menuMapper.findMenu( item.getPath() );
                if (menu == null) {
                    continue;
                }
                menu = buildMenu( menu );
                root.addChild( menu );
                Meta meta = null;
                if (menu.getMetaId() != null) {
                    meta = metaMapper.findMeta( menu.getMetaId() );
                }
                menu.setMeta( meta );
            }
        }
        return root;
    }
}
