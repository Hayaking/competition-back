package cadc.controller;

import cadc.bean.holder.EnterHolder;
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
     * @param enterHolder
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object addJoin(@RequestBody EnterHolder enterHolder) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        int joinTypeId = enterHolder.getJoin().getJoinTypeId();
        boolean flag = false;
        // joinTypeId == 1 是小组赛
        if (joinTypeId == 1) {
            flag = joinService.createGroupJoin( student, enterHolder.getGroup(), enterHolder.getList(), enterHolder.getWorks(), enterHolder.getJoin() );
        } else {
            // joinTypeId == 2 是个人赛
            flag = joinService.createSingleJoin(student, enterHolder.getWorks(), enterHolder.getJoin());
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
