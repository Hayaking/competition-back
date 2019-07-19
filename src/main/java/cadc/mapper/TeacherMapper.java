package cadc.mapper;

import cadc.entity.Student;
import cadc.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from teacher where account = #{account} and password=#{password}")
    Teacher find(@Param("account") String account, @Param("password") String password);

    @Select( "SELECT account,teacher.teacher_name FROM teacher JOIN teacher_in_group on teacher_in_group.teacher_id = teacher.account where teacher_in_group.group_id = #{groupId}" )
    List<Teacher> getByGroupId(int groupId);

}
