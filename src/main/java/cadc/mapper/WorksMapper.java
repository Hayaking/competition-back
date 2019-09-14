package cadc.mapper;

import cadc.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface WorksMapper extends BaseMapper<Works> {
    @Select("select * from works where id = #{id}")
    Works getById(@Param( "id" ) int id);
}
