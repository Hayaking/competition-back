package cadc.mapper;

import cadc.entity.JoinType;
import cadc.entity.PriceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface PriceTypeMapper extends BaseMapper<PriceType> {
    @Select( value = "select * from type_price where id = #{id}")
    PriceType getById(int id);
}
