package cadc.mapper;

import cadc.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author haya
 */
@Component
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from student where account = #{account} and password=#{password}")
    Student find(@Param("account") String account, @Param("password") String password);

    @Insert("insert into student(account,password) values(#{acc},#{psw})")
    int insertStudent(@Param("acc") String account, @Param("psw") String password);
}
