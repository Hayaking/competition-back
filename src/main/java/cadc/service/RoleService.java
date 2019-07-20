package cadc.service;

import cadc.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface RoleService extends IService<Role> {
    /**
     * 找到指定学生拥有的角色
     * @param account
     * @return
     */
    List<Role> findStudent(String account);

    /**
     * 找到指定老师拥有的角色
     * @param account
     * @return
     */
    List<Role> findTeacher(String account);

    /**
     * 删除指定学生的角色
     * @param account
     * @param roleId
     * @return
     */
    boolean deleteStudent(String account, int roleId);

    /**
     * 添加指定学生的指定角色
     * @param account
     * @param roleId
     * @return
     */
    boolean addStudent(String account, int roleId);

    /**
     * 添加指定教师的指定角色
     * @param account
     * @param roleId
     * @return
     */
    boolean addTeacher(String account, int roleId);

    /**
     * 删除指定教师的指定角色
     * @param account
     * @param roleId
     * @return
     */
    boolean deleteTeacher(String account, int roleId);
}
