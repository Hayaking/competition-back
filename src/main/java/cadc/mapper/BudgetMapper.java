package cadc.mapper;

import cadc.entity.Budget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface BudgetMapper extends BaseMapper<Budget> {
    @Select( "select * from budget where id = #{id}" )
    Budget getById(int id);

    @Select( "select * from budget where progress_id = #{id}" )
    Budget getByProgressId(int id);
}
