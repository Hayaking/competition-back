package cadc.mapper;

import cadc.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select role.id,role.role_name from role_stu " +
            "join role on role_stu.role_id = role.id " +
            "join student on student.id = role_stu.stu_id " +
            "where student.id=#{id}")
    List<Role> findStudentRoles(int id);

    @Select("select role.id,role.role_name from role_teacher " +
            "join role on role_teacher.role_id = role.id " +
            "join teacher on teacher.id = role_teacher.teacher_id " +
            "where teacher.id=#{id}")
    List<Role> findTeacherRoles(int  id);
}
