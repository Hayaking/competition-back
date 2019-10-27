package cadc.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedList;

/**
 * @author haya
 */
@Configuration
public class ShiroConfig {
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager( securityManager() );
        return shiroFilterFactoryBean;
    }

    @Bean(name = "studentRealm")
    public StudentRealm stuRealm() {
        StudentRealm studentRealm = new StudentRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密算法
        matcher.setHashAlgorithmName( "MD5" );
        // 加密次数
        matcher.setHashIterations( 10 );
        studentRealm.setCredentialsMatcher( matcher );
        return studentRealm;
    }

    @Bean(name = "teacherRealm")
    public TeacherRealm teacherRealm() {
        TeacherRealm teacherRealm = new TeacherRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 加密算法
        matcher.setHashAlgorithmName( "MD5" );
        // 加密次数
        matcher.setHashIterations( 10 );
        teacherRealm.setCredentialsMatcher( matcher );
        return teacherRealm;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager();
//        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
//        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return sessionManager;
    }
    @Bean
    @DependsOn({"studentRealm","teacherRealm"})
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator( modularRealmAuthenticator() );
        securityManager.setAuthorizer( modularRealmAuthorizer() );
        LinkedList<Realm> list = new LinkedList<>();
        list.add( stuRealm() );
        list.add( teacherRealm() );
        securityManager.setRealms( list );
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager( securityManager() );
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean(name = "authenticator")
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        return modularRealmAuthenticator;
    }

    @Bean(name = "authorizer")
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        UserModularRealmAuthorizer userModularRealmAuthorizer = new UserModularRealmAuthorizer();
        return userModularRealmAuthorizer;
    }
}
