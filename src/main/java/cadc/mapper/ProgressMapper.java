package cadc.mapper;

import cadc.entity.Progress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface ProgressMapper extends BaseMapper<Progress> {

    @Select( "select * from progress where id = #{progressId}" )
    Progress getById(int progressId);

    @Results(id = "progress", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "competition_id", property = "competitionId"),
            @Result(column = "type_id", property = "typeId"),
            @Result(column = "start_state", property = "startState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "is_scan_enter_state", property = "isScanEnterState"),
            @Result(column = "is_scan_start_state", property = "isScanStartState"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "enter_start_time", property = "enterStartTime")
    })
    @Select("select * from progress where competition_id = #{competitionId}")
    List<Progress> getListByCompetitionId(int competitionId);

    @Results(id = "withBudget", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "competition_id", property = "competitionId"),
            @Result(column = "type_id", property = "typeId"),
            @Result(column = "start_state", property = "startState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "is_scan_enter_state", property = "isScanEnterState"),
            @Result(column = "is_scan_start_state", property = "isScanStartState"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
            @Result(column = "enter_start_time", property = "enterStartTime")
    })
    @Select("select * from progress where competition_id = #{competitionId}")
    List<Progress> getWithBudgetByCompetitionId(int competitionId);


    /**
     * 得到一个参赛的阶段
     * @param joinId
     * @return
     */
    @ResultMap( value = "progress")
    @Select("SELECT * FROM join_in_progress join progress ON join_in_progress.progress_id = progress.id WHERE join_in_progress.join_id = #{joinId}")
    List<Progress> getListByJoinId(@Param("joinId") int joinId);
}
