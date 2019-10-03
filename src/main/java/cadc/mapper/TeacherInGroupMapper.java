package cadc.mapper;

import cadc.entity.TeacherInGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author haya
 */
@Mapper
public interface TeacherInGroupMapper extends BaseMapper<TeacherInGroup> {
    @Update( "update teacher_in_group set state = #{state} where group_id = #{groupId} and teacher_id= #{teacherId}" )
    int updateState(@Param( "groupId" ) int groupId, @Param( "teacherId" ) int teacherId,@Param( "state" ) String state);
}
