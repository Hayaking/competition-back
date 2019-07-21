package cadc.mapper;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface TeacherGroupMapper extends BaseMapper<TeacherGroup> {
    @Select("select teacher_group.id,group_name ,group_state,group_creater,group_create_time from teacher_in_group " +
            "join teacher_group " +
            "on teacher_in_group.group_id = teacher_group.id " +
            "where teacher_in_group.teacher_id = #{account}")
    List<TeacherGroup> findByTeacherId(String account);

    @Update("update teacher_group set group_state = #{state} where id=#{groupId}")
    int updateState(@Param( "groupId" ) int groupId,@Param( "state" ) String state);
}
