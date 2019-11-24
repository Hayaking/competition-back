package cadc.mapper;

import cadc.entity.JoinInProgress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface JoinInProgressMapper extends BaseMapper<JoinInProgress> {
    @Select(value = "select * from join_in_progress where id = #{id}")
    JoinInProgress getById(int id);

    @Select(value = "select * from join_in_progress where join_id = #{joinId} and progress_id = #{progressId}")
    JoinInProgress getByJoinIdProgressId(int joinId, int progressId);


    @Results(id = "withProgress",value = {
            @Result(column = "id", property = "id"),
            @Result(column = "progress_id", property = "progressId"),
            @Result(column = "progress_id", property = "progress", one = @One(select = "cadc.mapper.ProgressMapper.getById")),
            @Result(column = "join_id", property = "joinId"),
            @Result(column = "review_state", property = "reviewState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "is_promotion", property = "isPromotion"),
            @Result(column = "is_price", property = "isPrice"),
            @Result(column = "is_join", property = "isJoin"),
            @Result(column = "is_editable", property = "isEditable"),
            @Result(column = "is_self_funded", property = "isSelfFunded"),
    })
    @Select(value = "select * from join_in_progress where join_id = #{joinId}")
    List<JoinInProgress> getListByJoinId(int joinId);

    @Results(id = "withProgressAndJoin",value = {
            @Result(column = "id", property = "id"),
            @Result(column = "progress_id", property = "progressId"),
            @Result(column = "progress_id", property = "progress", one = @One(select = "cadc.mapper.ProgressMapper.getById")),
            @Result(column = "join_id", property = "joinId"),
            @Result(column = "join_id", property = "join", one = @One(select = "cadc.mapper.JoinMapper.getById")),
            @Result(column = "review_state", property = "reviewState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "is_promotion", property = "isPromotion"),
            @Result(column = "is_price", property = "isPrice"),
            @Result(column = "is_join", property = "isJoin"),
            @Result(column = "is_editable", property = "isEditable"),
            @Result(column = "is_self_funded", property = "isSelfFunded"),
    })
    @Select( value = "select * from join_in_progress where progress_id = #{progressId}" )
    List<JoinInProgress> getEnterList(Page<JoinInProgress> page, int progressId);

    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "progress_id", property = "progressId"),
            @Result(column = "join_id", property = "joinId"),
            @Result(column = "join_id", property = "join", one = @One(select = "cadc.mapper.JoinMapper.getById")),
            @Result(column = "review_state", property = "reviewState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "is_promotion", property = "isPromotion"),
            @Result(column = "is_price", property = "isPrice"),
            @Result(column = "is_join", property = "isJoin"),
            @Result(column = "is_editable", property = "isEditable"),
            @Result(column = "is_self_funded", property = "isSelfFunded"),
    })
    @Select( value = "select * from join_in_progress where progress_id = #{progressId}")
    List<JoinInProgress> getResultListByProgressId(Page<JoinInProgress> page, int progressId);

    @Delete( "delete from join_in_progress where join_id = #{joinId}" )
    int deleteByJoinId(int joinId);
}
