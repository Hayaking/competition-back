package cadc.mapper;

import cadc.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from teacher where account = #{account} and password=#{password}")
    Teacher find(@Param("account") String account, @Param("password") String password);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "account", property = "account"),
            @Result(column = "teacher_name", property = "teacherName"),
            @Result(column = "state", property = "state")
    })
    @Select( "SELECT teacher.id, teacher.account, teacher.teacher_name, teacher_in_group.state FROM teacher JOIN teacher_in_group on teacher_in_group.teacher_id = teacher.id where teacher_in_group.group_id = #{groupId}" )
    List<Teacher> getByGroupId(int groupId);

    @Select( "SELECT account,teacher.teacher_name FROM teacher JOIN teacher_in_group on teacher_in_group.teacher_id = teacher.account where teacher_in_group.state = '邀请中' AND teacher_in_group.group_id = #{groupId}" )
    List<Teacher> getInvitingByGroupId(int groupId);

    @Results({
            @Result(column = "account", property = "account"),
            @Result(column = "teacher_name", property = "teacherName"),
            @Result(column = "teacher_sex", property = "teacherSex"),
            @Result(column = "teacher_phone", property = "teacherPhone"),
            @Result(column = "teacher_master", property = "teacherMaster"),
            @Result(column = "teacher_level", property = "teacherLevel"),
    })
    @Select( "SELECT * FROM teacher JOIN role_teacher on teacher.id = role_teacher.teacher_id WHERE role_teacher.role_id =#{roleId}" )
    List<Teacher> getByRoleId(int roleId);

    @Select("Select * from teacher where id = #{id}")
    Teacher getById(int id);

}
