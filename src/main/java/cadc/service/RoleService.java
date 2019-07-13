package cadc.service;

import cadc.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据指定用户账号 找到其拥有的角色
     * @param account 
     * @return
     */
    List<Role> findStudent(String account);

    List<Role> findTeacher(String account);
}
