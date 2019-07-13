package cadc.mapper;

import cadc.entity.Student;
import cadc.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author haya
 */
@Component
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("select * from teacher where account = #{account} and password=#{password}")
    Teacher find(@Param("account") String account, @Param("password") String password);
}
