package cadc.mapper;

import cadc.entity.Menu1;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface Menu1Mapper extends BaseMapper<Menu1> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "cadc.mapper.Menu2Mapper.getByFatherId")),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("select * from menu1 where id = #{id}")
    Menu1 getWithChildrenById(int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("select * from menu1 where id = #{id}")
    Menu1 getById(int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "children", many = @Many(select = "cadc.mapper.Menu2Mapper.getByFatherId")),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("select * from menu1")
    List<Menu1> getAll();
}
