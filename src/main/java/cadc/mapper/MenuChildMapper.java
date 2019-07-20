package cadc.mapper;

import cadc.entity.Menu;
import cadc.entity.MenuChild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface MenuChildMapper extends BaseMapper<MenuChild> {
    @Select("SELECT path,name,component,meta_id FROM menu_children " +
            "join menu on menu_children.menu_child = menu.id " +
            "where menu_children.menu_father = #{fatherId}")
    List<Menu> find(int fatherId);
}
