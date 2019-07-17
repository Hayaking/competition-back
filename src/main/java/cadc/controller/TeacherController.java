package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Teacher;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
@RequestMapping
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public Object getAll() {
        List<Teacher> list = teacherService.list();
        return MessageFactory.message( SUCCESS, list );
    }
    @RequestMapping(value = "/teacher/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Teacher> list = teacherService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, list );
    }
    @RequestMapping(value = "/teacher/{groupId}", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId) {
        List<Teacher> list = teacherService.getByGroupId(groupId);
        return MessageFactory.message( SUCCESS, list );
    }
}
