package cadc.config.shiro;


import cadc.entity.Permission;
import cadc.entity.Role;
import cadc.entity.Teacher;
import cadc.service.PermissionService;
import cadc.service.RoleService;
import cadc.service.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author haya
 */
@Log4j2
public class TeacherRealm extends AuthorizingRealm {
    @Autowired
    private TeacherService teacherService;
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
        log.info( "角色权限验证" );
        Teacher teacher = (Teacher) getAvailablePrincipal( principalCollection );
        Set<String> roles = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roleList = roleService.findTeacher( teacher.getAccount() );
        for (Role item : roleList) {
            roles.add( item.getRoleName() );
            List<Permission> permissionList = permissionService.findPermissionList( item.getId() );
            for (Permission item2 : permissionList) {
                info.addStringPermission( item2.getPermissionName() );
            }
        }
        info.setRoles( roles );
        log.info( "角色："+roles );
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
        log.info( "登录验证" );
        //拿到账号
        String account = (String) token.getPrincipal();
        //拿到密码
        String passWord = new String( (char[]) token.getCredentials() );
        Teacher teacher = teacherService.find( account, passWord );
        if (teacher == null || teacher.getAccount() == null) {
            throw new UnknownAccountException();
        }
        teacher.setPassword( "" );
        SecurityUtils.getSubject();
        return new SimpleAuthenticationInfo( teacher, passWord, getName() );
    }
}
