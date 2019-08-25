package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.entity.Student;
import cadc.entity.StudentGroup;
import cadc.entity.StudentInGroup;
import cadc.entity.Teacher;
import cadc.service.StudentGroupService;
import cadc.service.StudentInGroupService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Log4j2
@RestController
@RequestMapping("/studentGroup")
public class StudentGroupController {
    @Autowired
    private StudentGroupService studentGroupService;
    @Autowired
    private StudentInGroupService studentInGroupService;

    @RequestMapping(value = "/create/{groupName}", method = RequestMethod.POST)
    public Object create(@PathVariable String groupName) {
        // 获取创建者的账号
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        String account = student.getAccount();
        StudentGroup studentGroup = new StudentGroup( groupName, account, new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() ) );
        boolean flag = studentGroup.insert();

        return MessageFactory.message(  SUCCESS ,studentGroup.getId());
    }

    @RequestMapping(value = "/addMembers/{groupId}", method = RequestMethod.POST)
    public Object create(@RequestBody List<String> list, @PathVariable int groupId) {
        studentInGroupService.addList( list, groupId );
        return MessageFactory.message(  SUCCESS );
    }
}
