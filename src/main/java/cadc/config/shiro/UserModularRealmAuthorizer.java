package cadc.config.shiro;

import cadc.entity.Student;
import cadc.entity.Teacher;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author haya
 * 授权器
 */
@Log4j2
public class UserModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            if (primaryPrincipal instanceof Student) {
                if (realm instanceof StudentRealm) {
                    return ((StudentRealm) realm).isPermitted(principals, permission);
                }
            }
            if (primaryPrincipal instanceof Teacher) {
                if (realm instanceof TeacherRealm) {
                    return ((TeacherRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Object primaryPrincipal = principals.getPrimaryPrincipal();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            if (primaryPrincipal instanceof Student) {
                if (realm instanceof StudentRealm) {
                    return ((StudentRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            if (primaryPrincipal instanceof Teacher) {
                if (realm instanceof TeacherRealm) {
                    return ((TeacherRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
        }
        return false;
    }
}
