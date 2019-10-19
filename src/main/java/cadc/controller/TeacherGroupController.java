package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Teacher;
import cadc.entity.TeacherGroup;
import cadc.entity.TeacherInGroup;
import cadc.service.TeacherGroupService;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Log4j2
@RestController
public class TeacherGroupController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherGroupService teacherGroupService;


    /**
     * 创建工作组
     *
     * @param group
     * @return
     */
    @RequiresRoles("老师")
    @RequestMapping(value = "/teacherGroup/create", method = RequestMethod.POST)
    public Object create(@RequestBody TeacherGroup group) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        Boolean flag = teacherGroupService.create( group, teacher.getId() );
        return MessageFactory.message( flag );
    }

    /**
     * 获取所在的工作组
     *
     * @return
     */
    @RequiresRoles("老师")
    @RequestMapping(value = "/teacherGroup/list", method = RequestMethod.GET)
    public Object list() {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        int id = teacher.getId();
        List<TeacherGroup> list = teacherGroupService.findByTeacherId( id );
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 获取指定工作组里的所有教师
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/teacherGroup/{groupId}/teachers", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId) {
        List<Teacher> list = teacherService.getByGroupId(groupId);
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 分页获取所在的工作组
     *
     * @return
     */
    @RequiresRoles("老师")
    @RequestMapping(value = "/teacherGroup/list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object listByPage(@PathVariable int pageNum, @PathVariable int pageSize) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        int id = teacher.getId();
        IPage<TeacherGroup> res = teacherGroupService.findPageByTeacherId( new Page<>( pageNum, pageSize ), id );
        return MessageFactory.message( res != null, res );
    }

    /**
     * 获取所有工作组
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles("管理员")
    @RequestMapping(value = "/teacherGroup/all/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object all(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<TeacherGroup> res = teacherGroupService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 邀请工作组成员
     *
     * @param groupId
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/teacherGroup/invite/{groupId}/{teacherId}", method = RequestMethod.POST)
    public Object invite(@PathVariable int groupId, @PathVariable int teacherId) {
        boolean flag = teacherGroupService.inviteTeacher( groupId, teacherId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 获取工作组的邀请
     *
     * @return
     */
    @RequestMapping(value = "/teacherGroup/inviting", method = RequestMethod.GET)
    public Object getInviting() {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<TeacherGroup> list = teacherGroupService.getInviting( teacher.getId() );
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 同意邀请
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/teacherGroup/agree/{groupId}", method = RequestMethod.POST)
    public Object agree(@PathVariable int groupId) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        int id = teacher.getId();
        boolean flag = teacherGroupService.updateState( groupId, id, STATE_INVITE_SUCCESS.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 拒绝邀请
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/teacherGroup/refuse/{groupId}", method = RequestMethod.POST)
    public Object refuse(@PathVariable int groupId) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        int id = teacher.getId();
        boolean flag = teacherGroupService.updateState( groupId, id, STATE_INVITE_FAILED.toString() );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 审核
     *
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping(value = "/teacherGroup/state/{id}/{flag}", method = RequestMethod.POST)
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
     *
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/teacherGroup/search/{key}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object search(@PathVariable String key, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<TeacherGroup> res = teacherGroupService.find( new Page<>( pageNum, pageSize ), key );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 退出工作组
     * @param groupId
     * @return
     */
    @RequestMapping(value = "teacherGroup/{groupId}", method = RequestMethod.DELETE)
    public Object exit(@PathVariable int groupId) {
        Subject subject = SecurityUtils.getSubject();
        Teacher teacher = (Teacher) subject.getPrincipal();
        int teacherId = teacher.getId();
        boolean flag = teacherGroupService.exit( groupId, teacherId );
        return MessageFactory.message( flag );
    }

    /**
     * 组长将组员移除工作组
     * @param groupId
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "teacherGroup/{groupId}/{teacherId}", method = RequestMethod.DELETE)
    public Object exit(@PathVariable int groupId, @PathVariable int teacherId) {
        TeacherInGroup teacherInGroup = new TeacherInGroup();
        UpdateWrapper<TeacherInGroup> wrapper = new UpdateWrapper<>();
        wrapper.eq( "group_id", groupId )
                .eq( "teacher_id", teacherId );
        boolean flag = teacherInGroup.delete( wrapper );
        return MessageFactory.message( flag );
    }
}
