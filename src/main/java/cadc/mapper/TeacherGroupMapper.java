package cadc.mapper;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import sun.security.krb5.internal.TGSRep;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface TeacherGroupMapper extends BaseMapper<TeacherGroup> {
    /**
     * 获取所在工作组
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "state", property = "state"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.TeacherMapper.getById"))
    })
    @Select("SELECT teacher_group.id, name, teacher_group.state, creator_id, create_time\n" +
            "FROM teacher_in_group " +
            "JOIN teacher_group ON teacher_in_group.group_id = teacher_group.id \n" +
            "WHERE teacher_group.state = '通过' " +
            "AND teacher_in_group.state = '邀请成功' " +
            "AND teacher_in_group.teacher_id = #{id}")
    List<TeacherGroup> findByTeacherId(int id);

    /**
     * 分页获取所在工作组
     * @param page
     * @param id
     * @return
     */
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "state", property = "state"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "reason", property = "reason"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.TeacherMapper.getById"))
    })
    @Select("SELECT teacher_group.id, name, teacher_group.state, creator_id, create_time, reason\n" +
            "FROM teacher_in_group " +
            "JOIN teacher_group ON teacher_in_group.group_id = teacher_group.id \n" +
            "WHERE teacher_in_group.state = '邀请成功' " +
            "AND teacher_in_group.teacher_id = #{id}")
    List<TeacherGroup> findByTeacherIdByPage(IPage<TeacherGroup> page, @Param("id") int id);

    @Update("update teacher_group set state = #{state} where id=#{groupId}")
    int updateState(@Param("groupId") int groupId, @Param("state") String state);

    @Select( "SELECT teacher_group.id,name, teacher_group.state, creator_id, create_time " +
            "FROM teacher_in_group " +
            "JOIN teacher_group ON teacher_group.id = teacher_in_group.group_id " +
            "WHERE teacher_in_group.state = '邀请中' AND teacher_in_group.teacher_id = #{id}" )
    List<TeacherGroup> getInvitingByTeacherId(int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "state", property = "state"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "reason", property = "reason"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.TeacherMapper.getById"))
    })
    @Select( "Select * from teacher_group" )
    List<TeacherGroup> getAllByPage(IPage<TeacherGroup> page);
}
