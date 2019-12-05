package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Message;
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
import org.springframework.web.bind.annotation.*;

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
        Map<String, Object> res = messageService.getMessage(subject);
        return MessageFactory.message( res != null, res );
    }

    @PostMapping(value = "/message/{id}")
    public Object setRead(@PathVariable String id) {
        Message message = messageService.getById( id );
        message.setIsRead( true );
        boolean res = message.insertOrUpdate();
        return MessageFactory.message( res );
    }
}
