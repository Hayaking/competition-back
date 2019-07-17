package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.service.StudentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
@RequestMapping
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    public Object update(@RequestBody Student student) {
        boolean flag = studentService.updateById( student );
        log.info( student );
        return MessageFactory.message( flag?SUCCESS:FAILED, "" );
    }

    @RequestMapping(value = "/student/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Student> res = studentService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }
}
