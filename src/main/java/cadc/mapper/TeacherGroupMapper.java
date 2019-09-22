package cadc.mapper;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface TeacherGroupMapper extends BaseMapper<TeacherGroup> {
    /**
     * 获取所在工作组
     */
    @Select("SELECT teacher_group.id,name,teacher_group.state,creator,create_time\n" +
            "FROM teacher_in_group " +
            "JOIN teacher_group ON teacher_in_group.group_id = teacher_group.id \n" +
            "WHERE teacher_group.state = '通过' " +
            "AND teacher_in_group.state = '邀请成功' " +
            "AND teacher_in_group.teacher_id = #{id}")
    List<TeacherGroup> findByTeacherId(int id);

    @Update("update teacher_group set state = #{state} where id=#{groupId}")
    int updateState(@Param("groupId") int groupId, @Param("state") String state);


    @Insert("insert into teacher_group (name, state, creator,create_time) " +
            "values(#{g.name},#{g.state},#{g.creator},#{g.createTime})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="g.id", before=false, resultType=Integer.class)
    int add(@Param( "g" ) TeacherGroup group);


    @Select( "SELECT teacher_group.id,name, teacher_group.state, creator, create_time " +
            "FROM teacher_in_group " +
            "JOIN teacher_group ON teacher_group.id = teacher_in_group.group_id " +
            "WHERE teacher_in_group.state = '邀请中' AND teacher_in_group.teacher_id = #{id}" )
    List<TeacherGroup> getInvitingByTeacherId(int id);
}
