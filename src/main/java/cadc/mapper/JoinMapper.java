package cadc.mapper;

import cadc.entity.Join;
import cadc.entity.StudentGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface JoinMapper extends BaseMapper<Join> {
    String GET_LIST_STU_ACCOUNT = "SELECT " +
            "join.id, works_id, competition_id, teacher_id1, teacher_id2, " +
            "apply_state, enter_state, join_state " +
            "FROM student " +
            "JOIN stu_in_group on stu_in_group.stu_id = student.id\n" +
            "JOIN stu_group on stu_group.id = stu_in_group.group_id\n" +
            "JOIN works on works.stu_group_id = stu_group.id\n" +
            "JOIN `join` on `join`.works_id = works.id\n" +
            "where student.account = #{account}";


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
    List<Join> getListByStudentAccount(String account);

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
