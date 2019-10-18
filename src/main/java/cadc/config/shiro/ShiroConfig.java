package cadc.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author haya
 */
@Configuration
public class ShiroConfig {
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager( securityManager );
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/static/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl( "/user/login" );
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl( "/index" );

        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl( "/404" );
        shiroFilterFactoryBean.setFilterChainDefinitionMap( filterChainDefinitionMap );
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

    @Bean(name = "SecurityManager")
    public SecurityManager securityManager() {
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
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager( securityManager );
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        return modularRealmAuthenticator;
    }

    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        UserModularRealmAuthorizer userModularRealmAuthorizer = new UserModularRealmAuthorizer();
        return userModularRealmAuthorizer;
    }
}
