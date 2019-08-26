package cadc.mapper;

import cadc.entity.Join;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
public interface JoinMapper extends BaseMapper<Join> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "worksId"),
            @Result(column = "competition_id", property = "competitionId"),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select( "SELECT * FROM student\n" +
            "JOIN stu_in_group on stu_in_group.stu_id = student.account\n" +
            "JOIN stu_group on stu_group.id = stu_in_group.group_id\n" +
            "JOIN works on works.stu_group_id = stu_group.id\n" +
            "JOIN `join` on `join`.works_id = works.id" )
    List<Join> getListByStudentAccount(String account);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "worksId"),
            @Result(column = "competition_id", property = "competitionId"),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "enter_state", property = "enterState"),
            @Result(column = "join_state", property = "joinState"),
    })
    @Select( "SELECT * FROM join WHERE join.teacher_id1 = #{account} or join.teacher_id2 = #{account}" )
    List<Join> getListByTeacherAccount(String account);
}
