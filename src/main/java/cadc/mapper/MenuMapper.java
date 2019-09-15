package cadc.mapper;

import cadc.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("SELECT menu.id as id,path,name,component,meta_id FROM permission_menu " +
            "join menu on permission_menu.menu_id = menu.id " +
            "join permission on permission_menu.permission_id = permission.id " +
            "where permission.id = #{id} and menu.component = 'Main' ")
    Menu getMainMenu(@Param("id") int permissionId);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("SELECT menu.id as id,path,name,component,meta_id FROM menu_children JOIN menu ON menu_children.menu_child = menu.id  where menu_father = #{fatherId}")
    List<Menu> getChildList(int fatherId);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta",
                    one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
            @Result(column = "meta_id", property = "metaId")
    })
    @Select("SELECT * from menu ")
    List<Menu> getAll();
}
