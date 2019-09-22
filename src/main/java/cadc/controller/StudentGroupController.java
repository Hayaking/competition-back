package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Student;
import cadc.entity.StudentGroup;
import cadc.entity.StudentInGroup;
import cadc.service.StudentGroupService;
import cadc.service.StudentInGroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class StudentGroupController {
    @Autowired
    private StudentGroupService studentGroupService;
    @Autowired
    private StudentInGroupService studentInGroupService;

    /**
     * 分页获取自己所在小组
     *
     * @return
     */
    @RequestMapping(value = "/studentGroup/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getByPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        IPage<StudentGroup> res = studentGroupService.getByStudentId( new Page<>( pageNum, pageSize ), student.getId() );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 学生创建小组
     *
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/studentGroup/create/{groupName}", method = RequestMethod.POST)
    public Object create(@PathVariable String groupName) {
        // 获取创建者的账号
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        int id = student.getId();
        StudentGroup studentGroup = new StudentGroup( groupName, id, new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( new Date() ) );
        boolean flag = studentGroup.insert();
        return MessageFactory.message( flag, studentGroup.getId() );
    }

    /**
     * 邀请小组成员
     *
     * @param list
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/studentGroup/addMembers/{groupId}", method = RequestMethod.POST)
    public Object invite(@RequestBody List<String> list, @PathVariable int groupId) {
        studentInGroupService.addList( list, groupId );
        return MessageFactory.message( SUCCESS );
    }
}
