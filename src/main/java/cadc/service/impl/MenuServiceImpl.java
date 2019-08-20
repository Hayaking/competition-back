package cadc.service.impl;

import cadc.entity.Menu;
import cadc.entity.Meta;
import cadc.entity.Permission;
import cadc.entity.Role;
import cadc.mapper.*;
import cadc.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * @author haya
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

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
    public List<Menu> getAll() {
        return menuMapper.getAll();
    }
}
