package cadc.service;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherGroupService extends IService<TeacherGroup> {

    Integer add(String groupName, String account, String state);
    /**
     * 查找教师所在工作组
     * @param account
     * @return
     */
    List<TeacherGroup> findByTeacherId(String account);

    IPage<TeacherGroup> findAll(IPage<TeacherGroup> page);

    /**
     * 邀请组员
     * @param groupId
     * @param account
     * @return
     */
    boolean inviteTeacher(int groupId, String account);

    List<TeacherGroup> getInviting(String account);

    /**
     * 更新邀请状态
     * @param groupId
     * @param account
     * @param state
     * @return
     */
    boolean updateState(int groupId, String account,String state);

    boolean addGroupMember(int groupId, String account);

    boolean setState(int groupId, String state);
}
