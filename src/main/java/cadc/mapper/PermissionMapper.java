package cadc.mapper;

import cadc.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 找到指定角色拥有的权限
     * @param roleId
     * @return
     */
    @Select("SELECT permission_id as id,permission_name  " +
            "FROM role_permission " +
            "join role on role_permission.role_id = role.id " +
            "join permission on role_permission.permission_id = permission.id " +
            "where role.id = #{roleId}")
    List<Permission> findList(int roleId);


}
