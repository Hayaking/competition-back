package cadc.service;

import cadc.entity.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 根据帐号密码查找
     * @param account
     * @param password
     * @return
     */
    Teacher find(String account, String password);

    List<Teacher> getByGroupId(int groupId);

    List<Teacher> getInvitingByGroupId(int groupId);

    IPage<Teacher> findAll(IPage<Teacher> page);
}
