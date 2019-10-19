package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.Menu1Mapper;
import cadc.mapper.Menu2Mapper;
import cadc.mapper.RoleMapper;
import cadc.mapper.RoleMenuMapper;
import cadc.service.Menu1Service;
import cadc.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Menu1ServiceImpl extends ServiceImpl<Menu1Mapper, Menu1> implements Menu1Service {
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private Menu1Mapper menu1Mapper;
    @Resource
    private Menu2Mapper menu2Mapper;

    @Override
    public Menu1 getById(int id) {
        return menu1Mapper.getWithChildrenById( id );
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
        // 获取RoleMenu
        Set<RoleMenu> set = new HashSet<>();
        for (Role item : roleList) {
            List<RoleMenu> list = roleMenuService.getListByRoleId( item.getId() );
            set.addAll( list );
        }
        // menu1可以对应多个menu2
        Map<Integer, List<Integer>> map = groupByMenu1Id( set );
        // 查询menu1 和menu2
        Set<Menu1> res = queryByMap( map );
        return new LinkedList<>( res );
    }


    @Override
    public List<Menu1> getAll() {
        return menu1Mapper.getAll();
    }

    @Override
    public List<Menu1> getByRoleId(int roleId) {
        // 获取RoleMenu
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq( "role_id", roleId );
        List<RoleMenu> roleMenuList = roleMenuMapper.selectList( wrapper );
        Map<Integer, List<Integer>> map = groupByMenu1Id( roleMenuList );
        Set<Menu1> res = queryByMap( map );
        return new LinkedList<>( res );
    }

    @Override
    public boolean setRoleAndMenu(int roleId, int menu1Id, int menu2Id, boolean flag) {
        if (flag) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenu1Id( menu1Id );
            roleMenu.setMenu2Id( menu2Id );
            roleMenu.setRoleId( roleId );
            return roleMenu.insert();
        } else {
            UpdateWrapper<RoleMenu> wrapper = new UpdateWrapper<>();
            wrapper.eq( "menu1_id", menu1Id );
            wrapper.eq( "menu2_id", menu2Id );
            return roleMenuMapper.delete( wrapper ) > 0;
        }
    }

    @Override
    public boolean setRoleAndMenu(int roleId, int menu1Id, boolean flag) {
        if (flag){
            // 获取menu1对应的全部menu2
            QueryWrapper<Menu2> menu2QueryWrapper = new QueryWrapper<>();
            menu2QueryWrapper.eq( "father_id", menu1Id );
            List<Menu2> menu2List = menu2Mapper.selectList( menu2QueryWrapper );
            List<Integer> all = menu2List.parallelStream().map( Menu2::getId ).collect( Collectors.toList() );
            // 获取menu1现有的对应的menu2
            QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
            roleMenuQueryWrapper.eq( "menu1_id", roleMenuQueryWrapper );
            List<RoleMenu> roleMenuList = roleMenuMapper.selectList( roleMenuQueryWrapper );
            List<Integer> some = roleMenuList.parallelStream().map( RoleMenu::getMenu2Id ).collect( Collectors.toList() );
            all.removeAll( some );
            for (Integer i : all) {
                new RoleMenu() {{
                    setMenu2Id( i );
                    setMenu1Id( menu1Id );
                    setRoleId( roleId );
                }}.insert();
            }
            return true;
        }else{
            UpdateWrapper<RoleMenu> wrapper = new UpdateWrapper<>();
            wrapper.eq( "menu1_id", menu1Id );
            wrapper.eq( "role_id", roleId );
            return roleMenuMapper.delete( wrapper )> 0;
        }
    }

    private Map<Integer, List<Integer>> groupByMenu1Id(Collection<RoleMenu> collection) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (RoleMenu item : collection) {
            int menu1Id = item.getMenu1Id();
            int menu2Id = item.getMenu2Id();
            List<Integer> list = map.containsKey( menu1Id )
                    ? map.get( menu1Id )
                    : new LinkedList<>();
            list.add( menu2Id );
            map.put( menu1Id, list );
        }
        return map;
    }

    private Set<Menu1> queryByMap(Map<Integer, List<Integer>> map) {
        Set<Menu1> res = new HashSet<>();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int menu1Id = entry.getKey();
            List<Integer> list = entry.getValue();
            Menu1 menu1 = menu1Mapper.getById( menu1Id );
            List<Menu2> menu2List = menu2Mapper.getByIdList( list );
            menu1.setChildren( menu2List );
            res.add( menu1 );
        }
        return res;
    }
}
