package cadc.config.shiro;


import cadc.entity.Permission;
import cadc.entity.Role;
import cadc.entity.Student;
import cadc.service.PermissionService;
import cadc.service.RoleService;
import cadc.service.StudentService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author haya
 */
@Log4j2
public class StudentRealm extends AuthorizingRealm {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 登录成功后 获取角色、权限等信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Student student = (Student) getAvailablePrincipal( principalCollection );
        Set<String> roles = new HashSet<>( 10 );
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.findStudent( student.getId() );
        for (Role item : roleList) {
            roles.add( item.getRoleName() );
            List<Permission> permissionList = permissionService.findPermissionList( item.getId() );
            for (Permission item2 : permissionList) {
                info.addStringPermission( item2.getPermissionName() );
            }
        }
        info.setRoles( roles );
        return info;
    }

    /**
     * 登录验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //拿到账号
        String account = (String) token.getPrincipal();
        Student user = studentService.find( account );
        if (user == null) {
            throw new UnknownAccountException();
        }
        ByteSource salt = ByteSource.Util.bytes( Long.toString( user.getSignTime().getTime() ) );
        return new SimpleAuthenticationInfo( user, user.getPassword(), salt, getName() );
    }

}
