package cadc.service;

import cadc.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
public interface RoleMenuService extends IService<RoleMenu> {
    List<RoleMenu> getListByRoleId(int roleId);
}
