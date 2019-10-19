package cadc.config.shiro;


import cadc.entity.Role;
import cadc.entity.Teacher;
import cadc.service.RoleService;
import cadc.service.TeacherService;
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
public class TeacherRealm extends AuthorizingRealm {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoleService roleService;

    /**
     * 登录成功后 获取角色、权限等信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Teacher teacher = (Teacher) getAvailablePrincipal( principalCollection );
        Set<String> roles = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> list = roleService.findTeacher( teacher.getId() );
        list.forEach( item-> roles.add( item.getRoleName() ) );
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
        Teacher teacher = teacherService.find( account );
        if (teacher == null ) {
            throw new UnknownAccountException();
        }
        ByteSource salt = ByteSource.Util.bytes( Long.toString( teacher.getSignTime().getTime() ) );
        return new SimpleAuthenticationInfo( teacher, teacher.getPassword(), salt, getName() );
    }
}
