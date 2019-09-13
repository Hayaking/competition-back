package cadc.controller;

import cadc.bean.Enter;
import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.entity.Join;
import cadc.entity.Student;
import cadc.entity.StudentGroup;
import cadc.entity.Works;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
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
public class JoinController {
    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object addJoin(@RequestBody Enter enter) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        boolean flag = joinService.create( student,enter.getGroup(), enter.getList(), enter.getWorks(), enter.getJoin() );
        return MessageFactory.message( flag ? SUCCESS : FAILED );
    }

    @RequestMapping(value = "/join/{id}", method = RequestMethod.DELETE)
    public Object deleteJoin(@PathVariable int id) {
        boolean flag = joinService.removeById( id );
        return MessageFactory.message( flag ? SUCCESS : FAILED );
    }

    @RequestMapping(value = "/join/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getJoinList(@PathVariable int pageNum, @PathVariable int pageSize) {
        Student stu = (Student) SecurityUtils.getSubject().getPrincipal();
        String account = stu.getAccount();
        IPage<Join> res = joinService.getByStudentAccount( new Page<>( pageNum, pageSize ), account );
        return MessageFactory.message( SUCCESS, res );
    }
}
