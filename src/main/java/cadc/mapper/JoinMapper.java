package cadc.mapper;

import cadc.entity.Join;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface JoinMapper extends BaseMapper<Join> {

    String GET_LIST_STU_ACCOUNT = "SELECT `join`.id, works_id, competition_id, teacher_id1, teacher_id2 , apply_state, enter_state, join_state FROM student JOIN stu_in_group ON stu_in_group.stu_id = student.id JOIN stu_group ON stu_group.id = stu_in_group.group_id JOIN `join` ON `join`.group_id = stu_group.id AND `join`.join_type_id = 1 WHERE student.id = #{id} UNION SELECT `join`.id, works_id, competition_id, teacher_id1, teacher_id2 , apply_state, enter_state, join_state FROM student JOIN `join` ON `join`.creator_id = student.id AND `join`.join_type_id = 2 WHERE student.id = #{id}";

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select(GET_LIST_STU_ACCOUNT)
    List<Join> getJoinListByStudentId(Page<Join> page, int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select("select * from `join` where competition_id = #{id}")
    List<Join> getListByCompetitionId(Page<Join> page, @Param("id") int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select("select * from `join` where competition_id = #{id}")
    List<Join> getListByCompetitionId(@Param("id") int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select("SELECT * FROM `join` WHERE teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> getListByTeacherId(Page<Join> page, @Param("id") int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select("SELECT * FROM `join` WHERE teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> searchListByTeacherId(Page<Join> page, @Param("id") int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select("SELECT * FROM `join` WHERE works_id = #{worksId} or competition_id = #{competitionId} or group_id = #{groupId} and teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> searchListByOtherId(Page<Join> page, @Param("worksId") int worksId, @Param("competitionId") int competitionId, @Param("groupId") int groupId, @Param("id") int teacherId);
}
