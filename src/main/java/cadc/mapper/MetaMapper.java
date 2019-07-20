package cadc.mapper;

import cadc.entity.Meta;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
@Mapper
public interface MetaMapper extends BaseMapper<Meta> {
    @Select( "select * from meta where id = #{id}" )
    Meta findMeta(@Param("id") int metaId);
}
