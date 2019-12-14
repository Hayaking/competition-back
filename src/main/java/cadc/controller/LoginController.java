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
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param token
     * @return
     */
    @RequiresGuest
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody UserToken token) {
        Subject subject = SecurityUtils.getSubject();
        // 已经登录
        if (subject.isAuthenticated()) {
            return MessageFactory.message( subject.getSession().getId() );
        }
        // 记住
        if (token.isRemember()) {
            token.setRememberMe( true );
        }
        subject.login( token );
        if (subject.isAuthenticated()) {
            return MessageFactory.message( subject.getSession().getId() );
        }
        // 失败
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
        subject.logout();
        return MessageFactory.message( "登出成功" );
    }

    /**
     * 注册
     *
     * @param student
     * @return
     */
    @RequiresGuest
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public Object sign(@RequestBody Student student) {
        boolean isExist = studentService.isExistByAccount( student.getAccount() );
        if (!isExist) {
            boolean flag = studentService.sign( student );
            return MessageFactory.message( flag, flag ? "注册成功" : "注册失败" );
        } else {
            return MessageFactory.message( false, "帐号已存在" );
        }
    }

    /**
     * 用户获取信息
     * @return
     */
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
        return MessageFactory.message( false, "获取失败" );
    }
}
