package cadc.mapper;

import cadc.entity.StudentGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author haya
 */
@Mapper
public interface StudentGroupMapper extends BaseMapper<StudentGroup> {
    @Select("select * from stu_group where id = #{id}")
    StudentGroup getById(int id);


}
