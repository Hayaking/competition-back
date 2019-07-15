package cadc.service;


import cadc.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获取路由菜单
     * @param account
     * @param type
     * @return
     */
    List<Menu> getMenu(String account, String type);

}
