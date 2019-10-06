package cadc.controller;

import cadc.bean.UserToken;
import cadc.bean.message.MessageFactory;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.service.StudentService;
import cadc.service.TeacherService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param type
     * @return
     */
    @RequestMapping(value = "/login/{type}", method = RequestMethod.POST)
    public Object login(String account, String password, @PathVariable String type) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // 已经登录
            return MessageFactory.message( SUCCESS, subject.getSession().getId() );
        }
        UserToken token = new UserToken( account, password, type );
        token.setRememberMe( true );
        subject.login( token );
        if (subject.isAuthenticated()) {
            return MessageFactory.message( SUCCESS, subject.getSession().getId() );
        }
        return MessageFactory.message( false );
    }

    /**
     * 登出
     *
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipal();
            log.info( principal );
            subject.logout();
            return MessageFactory.message( SUCCESS, "登出成功" );
        }
        return MessageFactory.message( FAILED, "登出失败" );
    }

    /**
     * 注册
     *
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Object sign(String account, String password) {
        log.info( "注册: " + account + "," + password );
        Student student = new Student( account, password );
        boolean insert = studentService.insert( student );
        return MessageFactory.message( SUCCESS, "注册成功" );
    }

    @RequiresAuthentication
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info() {
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal instanceof Teacher) {
            Teacher teacher = (Teacher) principal;
            teacher = teacherService.getById( teacher.getId() );
            return MessageFactory.message( teacher );
        } else if (principal instanceof Student) {
            Student student = (Student) principal;
            student = studentService.getById( student.getId() );
            return MessageFactory.message( student );
        }
        return MessageFactory.message( false );
    }
}
