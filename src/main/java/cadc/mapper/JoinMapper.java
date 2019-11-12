package cadc.mapper;

import cadc.entity.Join;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author haya
 */
public interface JoinMapper extends BaseMapper<Join> {

    String GET_LIST_STU_ACCOUNT = "SELECT `join`.id, works_id, competition_id, teacher_id1, teacher_id2 , apply_state, enter_state, join_state FROM student JOIN stu_in_group ON stu_in_group.stu_id = student.id JOIN stu_group ON stu_group.id = stu_in_group.group_id JOIN `join` ON `join`.group_id = stu_group.id AND `join`.join_type_id = 1 WHERE student.id = #{id} UNION SELECT `join`.id, works_id, competition_id, teacher_id1, teacher_id2 , apply_state, enter_state, join_state FROM student JOIN `join` ON `join`.creator_id = student.id AND `join`.join_type_id = 2 WHERE student.id = #{id}";

    @Select(value = "select * from `join` where id =#{id}")
    Join getById(int id);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competitionId"),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacher1",
                    one = @One(select = "cadc.mapper.TeacherMapper.getById")),
            @Result(column = "teacher_id2", property = "teacher2",
                    one = @One(select = "cadc.mapper.TeacherMapper.getById")),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "apply_state2", property = "applyState2")
    })
    @Select(GET_LIST_STU_ACCOUNT)
    List<Join> getJoinListByStudentId(Page<Join> page, int id);

    @ResultMap(value = {"withWorksAndCompetition"})
    @Select("select * from `join` where competition_id = #{id}")
    List<Join> getListByCompetitionId(Page<Join> page, @Param("id") int id);

    @Results(id = "withWorksAndCompetition",
        value = {
                @Result(column = "id", property = "id"),
                @Result(column = "works_id", property = "works",
                        one = @One(select = "cadc.mapper.WorksMapper.getById")),
                @Result(column = "competition_id", property = "competition",
                        one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
                @Result(column = "teacher_id1", property = "teacherId1"),
                @Result(column = "teacher_id2", property = "teacherId2"),
                @Result(column = "apply_state", property = "applyState"),
                @Result(column = "creator_id", property = "creatorId"),
                @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.StudentMapper.getStudentById"))
        })
    @Select("select * from `join` where competition_id = #{id}")
    List<Join> getListByCompetitionId(@Param("id") int id);

    @ResultMap( value = "withWorksAndCompetition")
    @Select("SELECT * FROM `join` JOIN join_in_progress ON join_in_progress.progress_id = #{progressId} WHERE competition_id = #{competitionId}")
    List<Join> getListByCompetitionIdProgressId(@Param("competitionId") int competitionId, @Param( "progressId" ) int progressId);

    @ResultMap(value = {"withWorksAndCompetition"})
    @Select("SELECT * FROM `join` WHERE teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> getListByTeacherId(Page<Join> page, @Param("id") int id);

    @ResultMap(value = {"withWorksAndCompetition"})
    @Select("SELECT * FROM `join` WHERE teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> searchListByTeacherId(Page<Join> page, @Param("id") int id);

    @ResultMap(value = {"withWorksAndCompetition"})
    @Select("SELECT * FROM `join` WHERE works_id = #{worksId} or competition_id = #{competitionId} or group_id = #{groupId} and teacher_id1 = #{id} or teacher_id2 = #{id}")
    List<Join> searchListByOtherId(Page<Join> page,
                                   @Param("worksId") int worksId,
                                   @Param("competitionId") int competitionId,
                                   @Param("groupId") int groupId,
                                   @Param("id") int teacherId);

    @Results(id = "withCompetition",
            value = {
                    @Result(column = "id", property = "id"),
                    @Result(column = "works_id", property = "worksId"),
                    @Result(column = "competition_id", property = "competition",
                            one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
                    @Result(column = "teacher_id1", property = "teacherId1"),
                    @Result(column = "teacher_id2", property = "teacherId2"),
                    @Result(column = "apply_state", property = "applyState"),
            })
    @Select("select * from `join` where group_id = #{groupId}")
    List<Join> getSimpleListByGroupId(@Param("groupId") int groupId);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "{joinId = id, progressId = progressId}", property = "inProgress",
                    one = @One(select = "cadc.mapper.JoinInProgressMapper.getByJoinIdProgressId")),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "competition_id", property = "competition",
                    one = @One(select = "cadc.mapper.CompetitionMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.StudentMapper.getStudentById"))
    })
    @Select("SELECT #{progressId} as progressId ,`join`.id, `join`.works_id, `join`.competition_id, `join`.group_id, `join`.teacher_id1, `join`.teacher_id2, `join`.apply_state, `join`.apply_state2, `join`.enter_state, `join`.join_state, `join`.join_type_id, `join`.creator_id, `join`.create_time FROM `join` JOIN join_in_progress ON `join`.id = join_in_progress.join_id WHERE `join`.competition_id = #{competitionId} AND join_in_progress.progress_id = #{progressId}")
    List<Join> getEnterList(Page<Join> page,
                            @Param( "competitionId" ) int competitionId,
                            @Param( "progressId" ) int progressId);


    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "works_id", property = "works",
                    one = @One(select = "cadc.mapper.WorksMapper.getById")),
            @Result(column = "teacher_id1", property = "teacherId1"),
            @Result(column = "teacher_id2", property = "teacherId2"),
            @Result(column = "apply_state", property = "applyState"),
            @Result(column = "state", property = "state"),
            @Result(column = "creator_id", property = "creatorId"),
            @Result(column = "creator_id", property = "creator", one = @One(select = "cadc.mapper.StudentMapper.getStudentById"))
    })
    @Select("SELECT * FROM join_in_progress JOIN `join` ON `join`.id = join_in_progress.join_id WHERE join_in_progress.progress_id =#{progressId} AND join_in_progress.price_state = TRUE ")
    List<Join> getListByProgressId(Page<Join> page, int progressId);
}
