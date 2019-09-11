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
     * @param id
     * @param type
     * @return
     */
    List<Menu> getMenu(int id, String type);

    List<Menu> getAll();

}
