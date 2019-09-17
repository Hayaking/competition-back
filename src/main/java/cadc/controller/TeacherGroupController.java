package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Log4j2
@RestController
@RequestMapping("/teacherGroup")
public class TeacherGroupController {
    @Autowired
    private TeacherGroupService teacherGroupService;

    /**
     * 创建工作组
     *
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/create/{groupName}", method = RequestMethod.POST)
    public Object create(@PathVariable String groupName) {
        // 获取创建者的账号
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String account = teacher.getAccount();
        // 添加工作组
        Integer id = teacherGroupService.add( groupName, account, STATE_APPLYING.toString());
        boolean flag;
        if (id != null) {
            // 将创建者添加进工作组
            flag = teacherGroupService.addGroupMember( id, account );
        } else {
            flag = false;
        }
        return MessageFactory.message( flag ? SUCCESS : FAILED );
    }

    /**
     * 获取所在的工作组
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String account = teacher.getAccount();
        List<TeacherGroup> list = teacherGroupService.findByTeacherId( account );
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 获取所有工作组
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object all(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<TeacherGroup> res = teacherGroupService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 邀请工作组成员
     *
     * @param groupId
     * @param account
     * @return
     */
    @RequestMapping(value = "/invite/{groupId}/{account}", method = RequestMethod.POST)
    public Object invite(@PathVariable int groupId, @PathVariable String account) {
        boolean flag = teacherGroupService.inviteTeacher( groupId, account );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 获取工作组的邀请
     * @return
     */
    @RequestMapping(value = "/inviting", method = RequestMethod.GET)
    public Object getInviting() {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<TeacherGroup> list = teacherGroupService.getInviting( teacher.getAccount() );
        return MessageFactory.message(SUCCESS, list );
    }

    /**
     * 同意邀请
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/agree/{groupId}", method = RequestMethod.POST)
    public Object agree(@PathVariable int groupId) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String account = teacher.getAccount();
        boolean flag = teacherGroupService.updateState( groupId, account, STATE_INVITE_SUCCESS.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 拒绝邀请
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/refuse/{groupId}", method = RequestMethod.POST)
    public Object refuse(@PathVariable int groupId) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String account = teacher.getAccount();
        boolean flag = teacherGroupService.updateState( groupId, account, STATE_INVITE_FAILED.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 审核
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping(value = "/state/{id}/{flag}", method = RequestMethod.POST)
    public Object review(@PathVariable int id, @PathVariable boolean flag) {
        if (flag) {
            flag = teacherGroupService.setState( id, STATE_AGREE.toString() );
        } else {
            flag = teacherGroupService.setState( id, STATE_REFUSE.toString() );
        }
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 搜索
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search/{key}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable String key, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<TeacherGroup> res = teacherGroupService.find( new Page<>( pageNum, pageSize ) , key );
        return MessageFactory.message( SUCCESS, res );
    }


}
