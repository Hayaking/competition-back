package cadc.controller;

import cadc.bean.Enter;
import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.entity.Student;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

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

    /**
     * 创建参赛
     * @param enter
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object addJoin(@RequestBody Enter enter) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        int joinTypeId = enter.getJoin().getJoinTypeId();
        boolean flag = false;
        // joinTypeId == 1 是小组赛
        if (joinTypeId == 1) {
            flag = joinService.createGroupJoin( student,enter.getGroup(), enter.getList(), enter.getWorks(), enter.getJoin() );
        } else {
            // joinTypeId == 2 是个人赛
            flag = joinService.createSingleJoin(student,enter.getWorks(),enter.getJoin());
        }
        return MessageFactory.message( flag);
    }

    @RequestMapping(value = "/join/{id}", method = RequestMethod.DELETE)
    public Object deleteJoin(@PathVariable int id) {
        boolean flag = joinService.removeById( id );
        return MessageFactory.message( flag );
    }

    @RequestMapping(value = "/join/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getJoinList(@PathVariable int pageNum, @PathVariable int pageSize) {
        Student stu = (Student) SecurityUtils.getSubject().getPrincipal();
        int id = stu.getId();
        IPage<Join> res = joinService.getByStudentId( new Page<>( pageNum, pageSize ), id );
        return MessageFactory.message( SUCCESS, res );
    }
}
