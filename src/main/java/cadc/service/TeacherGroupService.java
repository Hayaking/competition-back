package cadc.service;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherGroupService extends IService<TeacherGroup> {

    Integer add(String groupName, String account, String state);

    Boolean create(String groupName, int teacherId);
    /**
     * 查找教师所在工作组
     */
    List<TeacherGroup> findByTeacherId(int id);

    IPage<TeacherGroup> findAll(IPage<TeacherGroup> page);

    IPage<TeacherGroup> find(IPage<TeacherGroup> page, String key);
    /**
     * 邀请组员
     * @param groupId
     * @param account
     * @return
     */
    boolean inviteTeacher(int groupId, String account);

    List<TeacherGroup> getInviting(int id);

    /**
     * 更新邀请状态
     * @param groupId
     * @param account
     * @param state
     * @return
     */
    boolean updateState(int groupId, String account,String state);

    boolean addGroupMember(int groupId, int id);

    boolean setState(int groupId, String state);
}
