package cadc.service;

import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherGroupService extends IService<TeacherGroup> {

    Boolean create(TeacherGroup group, int teacherId);
    /**
     * 查找教师所在工作组
     */
    List<TeacherGroup> findByTeacherId(int id);

    IPage<TeacherGroup> findPageByTeacherId(IPage<TeacherGroup> page, int id);

    IPage<TeacherGroup> findAll(IPage<TeacherGroup> page);

    IPage<TeacherGroup> find(IPage<TeacherGroup> page, String key);
    /**
     * 邀请组员
     * @param groupId
     * @param account
     * @return
     */
    boolean inviteTeacher(Teacher leader,int groupId, int account);

    List<TeacherGroup> getInviting(int id);

    /**
     * 更新邀请状态
     * @param groupId
     * @param teacherId
     * @param state
     * @return
     */
    boolean updateState(int groupId, int teacherId,String state);

    boolean addGroupMember(int groupId, int id);

    boolean setState(int groupId, String state);

    boolean exit(int groupId, int teacherId);

    boolean delete(int groupId, int teacherId);

}
