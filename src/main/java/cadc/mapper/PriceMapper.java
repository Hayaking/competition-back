package cadc.mapper;

import cadc.entity.Price;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface PriceMapper extends BaseMapper<Price> {
    @Select( value = "select * from price where id = #{id}")
    Price getById(int id);
}
