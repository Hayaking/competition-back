package cadc.mapper;

import cadc.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT path,name,component,meta_id FROM permission_menu " +
            "join menu on permission_menu.menu_id = menu.path " +
            "join permission on permission_menu.permission_id = permission.id " +
            "where permission.id = #{id}")
    Menu findMainMenu(@Param("id") int permissionId);

    @Select("select path,name,component,meta_id from menu where path = #{path}")
    Menu findMenu(@Param("path") String path);
}
