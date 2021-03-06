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
public interface StudentGroupMapper extends BaseMapper<StudentGroup> {

    @Select("select * from stu_group where id = #{id}")
    StudentGroup getById(int id);


    /**
     * 查询学生组是 顺带查询组员
     * @param id
     * @return
     */
    @Results({
            @Result(column = "id", property = "members", one = @One(select = "cadc.mapper.StudentInGroupMapper.getStudentInGroupByGroupId")),
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "works_name", property = "worksName"),
    })
    @Select("select * from stu_group where id = #{id}")
    StudentGroup getStudentGroupWithMemberById(int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator",one = @One(select = "cadc.mapper.StudentMapper.getStudentById")),
            @Result(column = "works_name", property = "worksName"),
    })
    @Select("SELECT stu_group.id,stu_group.`name`,stu_group.creator_id,works.works_name\n" +
            "FROM `stu_group`\n" +
            "JOIN stu_in_group ON stu_group.id = stu_in_group.group_id\n" +
            "JOIN works on stu_group.id = works.stu_group_id\n" +
            "WHERE stu_in_group.state = '邀请成功' AND\n" +
            "stu_in_group.stu_id =  #{id}")
    List<StudentGroup> getWithWorksByStudentId(Page<StudentGroup> page, @Param( "id" ) int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator",one = @One(select = "cadc.mapper.StudentMapper.getStudentById")),
            @Result(column = "works_name", property = "worksName"),
    })
    @Select("SELECT stu_group.id,stu_group.`name`,stu_group.creator_id,works.works_name\n" +
            "FROM `stu_group`\n" +
            "JOIN stu_in_group ON stu_group.id = stu_in_group.group_id\n" +
            "JOIN works on stu_group.id = works.stu_group_id\n" +
            "WHERE stu_in_group.state = '邀请成功' AND\n" +
            "stu_in_group.stu_id =  #{id}")
    List<StudentGroup> getWithWorksByStudentId( @Param( "id" ) int id);


    @Select( "SELECT stu_group.id, `name`,creator_id,create_time,state \n" +
            "FROM stu_in_group\n" +
            "JOIN stu_group on stu_group.id = stu_in_group.group_id\n" +
            "WHERE stu_in_group.stu_id = #{id} " +
            "AND stu_in_group.state = '邀请中'")
    List<StudentGroup> getInvitingByStudentId(@Param("id") int id);

    @Select( "SELECT * \n" +
            "FROM `stu_group` \n" +
            "JOIN stu_in_group ON stu_group.id = stu_in_group.group_id\n" +
            "WHERE stu_in_group.state = '邀请成功' AND stu_in_group.stu_id =  #{id}" )
    List<StudentGroup> getList(int id);
}
