package cadc.mapper;

import cadc.entity.Menu;
import cadc.entity.MenuChild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
public interface MenuChildMapper extends BaseMapper<MenuChild> {
    @Select("SELECT path,name,component,meta_id FROM menu_children " +
            "join menu on menu_children.menu_child = menu.path " +
            "where menu_children.menu_father = #{fatherId}")
    List<Menu> find(String fatherId);
}
