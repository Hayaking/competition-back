package cadc.mapper;

import cadc.entity.WorkLoad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
public interface WorkLoadMapper extends BaseMapper<WorkLoad> {
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "val", property = "val"),
            @Result(column = "join_id", property = "joinId"),
            @Result(column = "join_id", property = "join", one = @One(select = "cadc.mapper.JoinMapper.getWithCompetitionById")),
            @Result(column = "teacher_id", property = "teacherId")
    })
    @Select(value = "select * from workload where teacher_id = #{id}")
    List<WorkLoad> getWorkLoadPage(Page<WorkLoad> page, int id);
}
