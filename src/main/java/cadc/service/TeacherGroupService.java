package cadc.service;

import cadc.entity.TeacherGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherGroupService extends IService<TeacherGroup> {
    /**
     * 查找教师所在工作组
     * @param account
     * @return
     */
    List<TeacherGroup> findByTeacherId(String account);

    /**
     * 邀请组员
     * @param groupId
     * @param account
     * @return
     */
    boolean inviteTeacher(int groupId, String account);

    /**
     * 更新状态
     * @param groupId
     * @param account
     * @param state
     * @return
     */
    boolean updateState(int groupId, String account,String state);
}
