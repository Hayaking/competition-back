package cadc.mapper;

import cadc.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haya
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select role.id,role.role_name from role_stu join role on role_stu.role_id = role.id join student on student.account = role_stu.stu_id")
    List<Role> findStudentRoles(String account);

    @Select("select role.id,role.role_name from role_teacher join role on role_teacher.role_id = role.id join teacher on teacher.account = role_teacher.teacher_id")
    List<Role> findTeacherRoles(String account);
}
