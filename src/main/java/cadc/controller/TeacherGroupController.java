package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/teacherGroup")
public class TeacherGroupController {
    @Autowired
    private TeacherGroupService teacherGroupService;

    /**
     * 创建工作组
     * @param groupName
     * @return
     */
    @RequiresPermissions( "创建工作组" )
    @RequestMapping(value = "/create/{groupName}", method = RequestMethod.POST)
    public Object create(@PathVariable String groupName) {
        Subject subject = SecurityUtils.getSubject();
        Teacher teacher = (Teacher) subject.getPrincipal();
        String account = teacher.getAccount();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TeacherGroup teacherGroup = new TeacherGroup( groupName, account, STATE_APPLYING.toString(), sdf.format( date ) );
        boolean flag = teacherGroupService.save( teacherGroup );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 获取所在的工作组
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list() {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String account = teacher.getAccount();
        List<TeacherGroup> list = teacherGroupService.findByTeacherId( account );
        log.info( list );
        return MessageFactory.message( SUCCESS, list );
    }

    @RequestMapping(value = "/all/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object all(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<TeacherGroup> res = teacherGroupService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 邀请工作组成员
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
     * 同意邀请
     * @param groupId
     * @param account
     * @return
     */
    @RequestMapping(value = "/agree/{groupId}/{account}", method = RequestMethod.POST)
    public Object agree(@PathVariable int groupId, @PathVariable String account) {
        boolean flag = teacherGroupService.updateState( groupId, account, STATE_INVITE_SUCCESS.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 拒绝邀请
     * @param groupId
     * @param account
     * @return
     */
    @RequestMapping(value = "/refuse/{groupId}/{account}", method = RequestMethod.POST)
    public Object refuse(@PathVariable int groupId, @PathVariable String account) {
        boolean flag = teacherGroupService.updateState( groupId, account, STATE_INVITE_FAILED.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    @RequestMapping(value = "/state/{id}/{flag}", method = RequestMethod.POST)
    public Object refuse(@PathVariable int id, @PathVariable boolean flag) {
        if (flag) {
            flag = teacherGroupService.setState( id, STATE_AGREE.toString() );
        } else {
            flag = teacherGroupService.setState( id, STATE_REFUSE.toString() );
        }
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }
}
