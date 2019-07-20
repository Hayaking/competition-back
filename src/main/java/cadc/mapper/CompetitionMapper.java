package cadc.mapper;

import cadc.entity.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author haya
 */
@Mapper
public interface CompetitionMapper extends BaseMapper<Competition> {

    @Update( "update competition set cp_state = #{state} where id=#{id}" )
    int updateState(int id, String state);

    @Update( "update competition set cp_enter_state = #{state} where id=#{id}" )
    int updateEnterState(int id, String state);
}
