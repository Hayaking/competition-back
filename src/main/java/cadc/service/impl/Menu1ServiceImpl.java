package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.Menu1Mapper;
import cadc.mapper.RoleMapper;
import cadc.service.Menu1Service;
import cadc.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    private Menu1Mapper menu1Mapper;

    @Override
    public Menu1 getById(int id) {
        return menu1Mapper.getWithChildsById( id );
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
        for (RoleMenu item : set) {
            Menu1 menu1 = menu1Mapper.getWithChildsById( item.getMenuId() );
            res.add( menu1 );
        }
        return new LinkedList<>( res );
    }
}
