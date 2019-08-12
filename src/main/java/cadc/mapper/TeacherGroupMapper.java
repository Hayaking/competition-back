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
     * @param account
     * @return
     */
    @Select("SELECT teacher_group.id,group_name,group_state,group_creater,group_create_time FROM teacher_in_group JOIN teacher_group ON teacher_in_group.group_id = teacher_group.id WHERE teacher_group.group_state = '通过' AND teacher_in_group.state = '邀请成功' AND teacher_in_group.teacher_id = #{account}")
    List<TeacherGroup> findByTeacherId(String account);

    @Update("update teacher_group set group_state = #{state} where id=#{groupId}")
    int updateState(@Param("groupId") int groupId, @Param("state") String state);


    @Insert("insert into teacher_group (group_name,group_state,group_creater,group_create_time) values(#{g.groupName},#{g.groupState},#{g.groupCreater},#{g.groupCreateTime})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="g.id", before=false, resultType=Integer.class)
    int add(@Param( "g" ) TeacherGroup group);


    @Select( "SELECT teacher_group.group_name,teacher_group.group_state,teacher_group.group_creater,teacher_group.group_create_time,teacher_group.id FROM teacher_in_group JOIN teacher_group ON teacher_group.id = teacher_in_group.group_id WHERE teacher_in_group.state = '邀请中' AND teacher_in_group.teacher_id = #{account}" )
    List<TeacherGroup> getInvitingByTeacherId(String account);
}
