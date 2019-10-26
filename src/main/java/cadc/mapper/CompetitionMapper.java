package cadc.mapper;

import cadc.entity.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface CompetitionMapper extends BaseMapper<Competition> {
    Result[] results = {};

    @Update( "update competition set state = #{state} where id=#{id}" )
    int updateState(int id, String state);

    @Update( "update competition set start_state = #{state} where id=#{id}" )
    int updateStartState(int id, String state);

    @Update( "update competition set enter_state = #{state} where id=#{id}" )
    int updateEnterState(int id, String state);

    @Select("select * from competition where id =#{id}")
    Competition getById(@Param( "id" )int id);

    /**
     * 根据工作组id 查询competition带上progress
     * @param page
     * @param id
     * @return
     */
    @Results(id = "withProgressList", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "progressList", many = @Many(select = "cadc.mapper.ProgressMapper.getWithBudgetByCompetitionId")),
            @Result(column = "name", property = "name"),
            @Result(column = "group_num", property = "groupNum"),
            @Result(column = "stu_num", property = "stuNum"),
            @Result(column = "ex_res", property = "exRes"),
            @Result(column = "place", property = "place"),
            @Result(column = "org", property = "org"),
            @Result(column = "co_org", property = "coOrg"),
            @Result(column = "state", property = "state"),
            @Result(column = "person_in_charge", property = "personInCharge"),
            @Result(column = "creator", property = "creator"),
            @Result(column = "process", property = "process"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "is_have_works", property = "isHaveWorks"),
            @Result(column = "teacher_group_id", property = "teacherGroupId"),
            @Result(column = "join_type_id", property = "joinTypeId"),
    })
    @Select("select * from competition where teacher_group_id =#{id}")
    List<Competition> getWithProgressListByTeacherGroupId(Page<Competition> page, @Param( "id" )int id);
}
