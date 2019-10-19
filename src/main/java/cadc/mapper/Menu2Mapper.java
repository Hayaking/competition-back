package cadc.mapper;

import cadc.entity.Menu2;
import cadc.mapper.model.Menu2Model;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface Menu2Mapper extends BaseMapper<Menu2> {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @Select("select * from menu2 where father_id = #{fatherId}")
    List<Menu2> getByFatherId(int fatherId);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "path", property = "path"),
            @Result(column = "name", property = "name"),
            @Result(column = "component", property = "component"),
            @Result(column = "meta_id", property = "meta", one = @One(select = "cadc.mapper.MetaMapper.getMetaById")),
    })
    @SelectProvider(type = Menu2Model.class, method = "getByIdList")
    List<Menu2> getByIdList(List<Integer> list);

}
