package cadc.mapper;

import cadc.entity.JoinInProgress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
public interface JoinInProgressMapper extends BaseMapper<JoinInProgress> {
    @Select(value = "select * from join_in_progress where id = #{id}")
    JoinInProgress getById(int id);

    @Select(value = "select * from join_in_progress where join_id = #{joinId} and progress_id = #{progressId}")
    JoinInProgress getByJoinIdProgressId(int joinId, int progressId);
}
