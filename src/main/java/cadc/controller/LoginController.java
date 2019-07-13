package cadc.controller;

import cadc.bean.UserToken;
import cadc.bean.message.Message;
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
     * @param account
     * @param password
     * @param type
     * @return
     */
    @RequestMapping(value = "/login/{type}", method = RequestMethod.POST)
    public Object login(String account, String password, @PathVariable String type) {
        log.info( "登录: " + account + "," + password + "," + type );
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return MessageFactory.message( SUCCESS, subject.getSession().getId() );
        }
        subject.login( new UserToken( account, password, type ) );
        if (subject.isAuthenticated()) {
            return MessageFactory.message( SUCCESS, subject.getSession().getId() );
        }
        return MessageFactory.message( SUCCESS, "" );
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
    @RequestMapping(value = "/info/{type}", method = RequestMethod.GET)
    public Object info(@PathVariable String type) {
        Subject subject = SecurityUtils.getSubject();
        Message res = MessageFactory.message( SUCCESS, "注册成功" );
        switch (type) {
            case "student":
                Student student = (Student) subject.getPrincipal();
                student = studentService.getById( student.getAccount() );
                res = MessageFactory.message( SUCCESS, student );
                break;
            case "teacher":
                Teacher teacher = (Teacher) subject.getPrincipal();
                teacher = teacherService.getById( teacher.getAccount() );
                res = MessageFactory.message( SUCCESS, teacher );
                break;
            default:
                break;
        }
        return res;
    }
}
