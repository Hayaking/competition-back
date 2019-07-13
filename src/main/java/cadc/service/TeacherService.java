package cadc.service;

import cadc.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
