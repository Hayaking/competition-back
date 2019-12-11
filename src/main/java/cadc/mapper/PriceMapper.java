package cadc.mapper;

import cadc.entity.Join;
import cadc.entity.Price;
import cadc.mapper.model.PriceModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface PriceMapper extends BaseMapper<Price> {
    @Select(value = "select * from price where id = #{id}")
    Price getById(int id);

    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "price_state", property = "priceState"),
            @Result(column = "price_time", property = "priceTime"),
            @Result(column = "type_id", property = "typeId"),
            @Result(column = "type_id", property = "type", one = @One(select = "cadc.mapper.PriceTypeMapper.getById")),
            @Result(column = "join_id", property = "joinId"),
            @Result(column = "join_id", property = "join", one = @One(select = "cadc.mapper.JoinMapper.getWithCompetitionById")),
            @Result(column = "join_in_progress_id", property = "joinInProgressId"),
            @Result(column = "state", property = "state")
    })
    @SelectProvider(type = PriceModel.class, method = "getPriceByStudent")
    List<Price> getPageByStudent(Page<Price> page, @Param("joinList") List<Join> joinList);
}
