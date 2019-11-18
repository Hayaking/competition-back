package cadc.service;

import cadc.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author haya
 */
public interface RoleService extends IService<Role> {
    /**
     * 找到指定学生拥有的角色
     * @param id
     * @return
     */
    List<Role> findStudent(int id);

    /**
     * 找到指定老师拥有的角色
     * @param id
     * @return
     */
    List<Role> findTeacher(int id);

    /**
     * 删除指定学生的角色
     * @param id
     * @param roleId
     * @return
     */
    boolean deleteStudent(int id, int roleId);

    /**
     * 添加指定学生的指定角色
     * @param id
     * @param roleId
     * @return
     */
    boolean addStudent(int id, int roleId);

    /**
     * 添加指定教师的指定角色
     * @param id
     * @param roleId
     * @return
     */
    boolean addTeacher(int id, int roleId);

    /**
     * 删除指定教师的指定角色
     * @param id
     * @param roleId
     * @return
     */
    boolean deleteTeacher(int id, int roleId);

    List<Role> getSelfRoleList(Subject subject);
}
