package cadc.service;


import cadc.entity.Menu;
import cadc.entity.Menu1;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获取路由菜单
     * @param id
     * @param type
     * @return
     */
    List<Menu> getMenu(int id, String type);

    List<Menu1> getMenuByUser(Object object);

    List<Menu> getAll();

}
