package cadc.service;


import cadc.entity.Menu;

import java.util.List;

/**
 * @author haya
 */
public interface MenuService {
    /**
     * 获取路由菜单
     * @param account
     * @param type
     * @return
     */
    List<Menu> getMenu(String account, String type);

}
