package cadc.mapper;

import cadc.entity.CompetitionType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface CompetitionTypeMapper extends BaseMapper<CompetitionType> {
    @Select("select * from type_competition where id = #{id}")
    CompetitionType getById(int id);
}
