package cadc.mapper;

import cadc.entity.StudentInGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author haya
 */
@Mapper
public interface StudentInGroupMapper extends BaseMapper<StudentInGroup> {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "stu_id", property = "id"),
            @Result(column = "group_id", property = "groupId"),
            @Result(column = "group_id", property = "group", one = @One(select = "cadc.mapper.StudentGroupMapper.getById")),
    })
    @Select("SELECT stu_in_group.id, stu_id, group_id FROM `stu_in_group`\n" +
            "join stu_group on stu_group.id = stu_in_group.group_id\n" +
            "WHERE stu_in_group.stu_id = #{id} and stu_in_group.state = '邀请成功' ")
    List<StudentInGroup> getByStudentId(@Param("id") int id);

    /**
     * 根据groupId查询 顺带根据stuId查询学生
     * @param id
     * @return
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "stu_id", property = "id"),
            @Result(column = "stu_id", property = "student", one = @One(select = "cadc.mapper.StudentMapper.getStudentById")),
            @Result(column = "group_id", property = "groupId")
    })
    @Select("select * from stu_in_group where group_id = #{id}")
    List<StudentInGroup> getStudentInGroupByGroupId(int id);

}
