package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.*;
import cadc.service.Menu1Service;
import cadc.service.MenuService;
import cadc.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author haya
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    public Menu1Service menu1Service;
    @Resource
    private RoleMenuService roleMenuService;
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
    public List<Menu> getMenu(int id, String type) {
        //获取角色
        List<Role> roleList;
        if ("student".equals( type )) {
            roleList = roleMapper.findStudentRoles( id );
        } else if ("teacher".equals( type )) {
            roleList = roleMapper.findTeacherRoles( id );
        } else {
            return null;
        }
        List<Permission> perList = new LinkedList<>();
        Set<Menu> res = new TreeSet<>( Comparator.comparingInt( Menu::getId ) );
        //获取权限
        for (Role item : roleList) {
            perList.addAll( permissionMapper.getList( item.getId() ) );
        }

        //获取菜单
        for (Permission item : perList) {
            Menu menu = menuMapper.getMainMenu( item.getId() );
            if (null == menu) {
                continue;
            }
            menu.setChildren( menuMapper.getChildList( menu.getId() ) );
            res.add( menu );
        }
        return new LinkedList<>( res );
    }

    @Override
    public List<Menu1> getMenuByUser(Object obj) {
        //获取角色
        List<Role> roleList;
        if (obj instanceof Student) {
            roleList = roleMapper.findStudentRoles( ((Student) obj).getId() );
        } else {
            roleList = roleMapper.findTeacherRoles( ((Teacher) obj).getId() );
        }
        Set<RoleMenu> set = new HashSet<>();
        for (Role item : roleList) {
            List<RoleMenu> list = roleMenuService.getListByRoleId( item.getId() );
            set.addAll( list );
        }
        Set<Menu1> res = new HashSet<>();
        for (RoleMenu item:set) {
            Menu1 menu1 = menu1Service.getById( item.getMenuId() );
            res.add( menu1 );
        }
        return new LinkedList<>( res );
    }

    @Override
    public List<Menu> getAll() {
        return menuMapper.getAll();
    }
}
