package cadc.mapper;

import cadc.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * @author haya
 */
public interface WorksMapper extends BaseMapper<Works> {
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "works_name", property = "worksName"),
        @Result(column = "stu_group_id", property = "studentGroup",
                one = @One(select = "cadc.mapper.StudentGroupMapper.getById"))
    })
    @Select("select * from works where id = #{id}")
    Works getById(@Param( "id" ) int id);
}
