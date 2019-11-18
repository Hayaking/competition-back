package cadc.mapper;

import cadc.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface WorksMapper extends BaseMapper<Works> {
    @Results(id = "withCreatorAndGroup",
        value = {
            @Result(column = "id", property = "id"),
            @Result(column = "works_name", property = "worksName"),
            @Result(column = "des", property = "des"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator",
                    one = @One(select = "cadc.mapper.StudentMapper.getStudentById")),
            @Result(column = "stu_group_id", property = "stuGroupId"),
            @Result(column = "stu_group_id", property = "studentGroup",
                    one = @One(select = "cadc.mapper.StudentGroupMapper.getStudentGroupWithMemberById"))
    })
    @Select("select * from works where id = #{id}")
    Works getById(@Param("id") int id);

    @Results(id = "withCreator",
        value = {
                @Result(column = "id", property = "id"),
                @Result(column = "works_name", property = "worksName"),
                @Result(column = "des", property = "des"),
                @Result(column = "creator_id", property = "creatorId"),
                @Result(column = "creator_id", property = "creator",
                        one = @One(select = "cadc.mapper.StudentMapper.getStudentById")),
                @Result(column = "stu_group_id", property = "stuGroupId")
    })
    @Select("select * from works where stu_group_id = #{groupId}")
    List<Works> getSimpleListByGroupId(@Param( "groupId" ) int groupId);





}
