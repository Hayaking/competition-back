package cadc.mapper;

import cadc.entity.StudentGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface StudentGroupMapper extends BaseMapper<StudentGroup> {

    @Select("select * from stu_group where id = #{id}")
    StudentGroup getById(int id);


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "creator", property = "creator"),
            @Result(column = "works_name", property = "worksName"),
    })
    @Select("SELECT stu_group.id,stu_group.`name`,stu_group.creator,works.works_name\n" +
            "FROM `stu_group`\n" +
            "JOIN stu_in_group ON stu_group.id = stu_in_group.group_id\n" +
            "JOIN works on stu_group.id = works.stu_group_id\n" +
            "WHERE stu_in_group.state = '邀请成功' AND\n" +
            "stu_in_group.stu_id =  #{id}")
    List<StudentGroup> getWithWorksByStudentId(Page<StudentGroup> page, @Param( "id" ) int id);
}
