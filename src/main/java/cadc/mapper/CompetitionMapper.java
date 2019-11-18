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

    @Update("update competition set state = #{state} where id=#{id}")
    int updateState(int id, String state);

    @Update("update competition set start_state = #{state} where id=#{id}")
    int updateStartState(int id, String state);

    @Update("update competition set enter_state = #{state} where id=#{id}")
    int updateEnterState(int id, String state);

    @Select("select * from competition where id =#{id}")
    Competition getById(@Param("id") int id);

    @ResultMap(value = "withProgressList")
    @Select("select * from competition where id =#{id}")
    Competition getWithProgressListById(@Param("id") int id);

    /**
     * 根据工作组id 查询competition带上progress
     *
     * @param page
     * @param id
     * @return
     */
    @Results(id = "withProgressList", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "progressList", many = @Many(select = "cadc.mapper.ProgressMapper.getListByCompetitionId")),
            @Result(column = "name", property = "name"),
            @Result(column = "ex_group_num", property = "exGroupNum"),
            @Result(column = "ex_stu_num", property = "exStuNum"),
            @Result(column = "ex_res", property = "exRes"),
            @Result(column = "state", property = "state"),
            @Result(column = "person_in_charge_id", property = "personInChargeId"),
            @Result(column = "person_in_charge_id", property = "personInCharge", one = @One(select = "cadc.mapper.TeacherMapper.getById")),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.TeacherMapper.getById")),
            @Result(column = "process", property = "process"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "ex_condition", property = "exCondition"),
            @Result(column = "is_need_works", property = "isNeedWorks"),
            @Result(column = "teacher_group_id", property = "teacherGroupId"),
            @Result(column = "join_type_id", property = "joinTypeId"),
            @Result(column = "crate_time", property = "crateTime"),
    })
    @Select("select * from competition where teacher_group_id =#{id}")
    List<Competition> getWithProgressListByTeacherGroupId(Page<Competition> page, @Param("id") int id);

    @ResultMap(value = "withProgressList")
    @Select(" select * from competition where state = 1")
    List<Competition> getPassList(Page<Competition> page);

    @ResultMap(value = "withProgressList")
    @Select(" select * from competition")
    List<Competition> getAllByPage(Page<Competition> page);

    @Results( value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "progressList", many = @Many(select = "cadc.mapper.ProgressMapper.getWithBudgetByCompetitionId")),
            @Result(column = "name", property = "name"),
            @Result(column = "ex_group_num", property = "exGroupNum"),
            @Result(column = "ex_stu_num", property = "exStuNum"),
            @Result(column = "ex_res", property = "exRes"),
            @Result(column = "state", property = "state"),
            @Result(column = "person_in_charge_id", property = "personInChargeId"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "process", property = "process"),
            @Result(column = "intro", property = "intro"),
            @Result(column = "is_need_works", property = "isNeedWorks"),
            @Result(column = "teacher_group_id", property = "teacherGroupId"),
            @Result(column = "join_type_id", property = "joinTypeId"),
            @Result(column = "crate_time", property = "crateTime"),
    })
    @Select("select * from competition where id =#{id}")
    Competition getWithBudgetListById( @Param("id") int id);

}
