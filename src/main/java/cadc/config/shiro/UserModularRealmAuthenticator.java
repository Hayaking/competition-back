package cadc.config.shiro;

import cadc.bean.UserToken;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author haya
 */
@Log4j2
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.info("=========================UserModularRealmAuthenticator=========================");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        UserToken userToken = (UserToken) authenticationToken;
        // 登录类型
        String loginType = userToken.getType();
        log.info( "type:"+loginType );
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        List<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            log.info( realm.getName().toLowerCase().contains( loginType ));
            if (realm.getName().toLowerCase().contains( loginType )) {
                log.info( realm.getName() );
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1){
            log.info("单个");
            return doSingleRealmAuthentication(typeRealms.get(0), userToken);
        }
        else{
            log.info("多个 ");
            return doMultiRealmAuthentication(typeRealms, userToken);
        }
    }
}
