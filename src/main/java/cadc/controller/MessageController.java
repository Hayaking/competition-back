package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.MessageService;
import cadc.service.TeacherGroupService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequiresAuthentication
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public Object getMessage() {
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();
        Map<String, Object> res = null;
        if (obj instanceof Student) {
            res = messageService.getStudentMessage( ((Teacher) obj).getAccount() );
        } else if ( obj instanceof Teacher) {
            res = messageService.getTeacherMessage(subject);
        }
        return MessageFactory.message(SUCCESS, res );
    }
}
