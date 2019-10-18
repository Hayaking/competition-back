package cadc.service.impl;

import cadc.entity.RoleMenu;
import cadc.mapper.RoleMenuMapper;
import cadc.service.RoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Queue;

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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenu> getListByRoleId(int roleId) {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq( "role_id", roleId );
        return roleMenuMapper.selectList( wrapper );
    }

}
