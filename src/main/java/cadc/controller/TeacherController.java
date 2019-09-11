package cadc.controller;

import cadc.bean.TEACHER_TYPE;
import cadc.bean.message.MessageFactory;
import cadc.entity.Role;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.service.RoleService;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Log4j2
@RestController
@RequestMapping
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有教师
     * @return
     */
    @RequestMapping(value = "/teacher", method = RequestMethod.GET)
    public Object getAll() {
        List<Teacher> list = teacherService.list();
        return MessageFactory.message( SUCCESS, list );
    }

    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public Object save(@RequestBody Teacher teacher) {
        boolean flag = teacherService.saveOrUpdate( teacher );
        return MessageFactory.message( flag ? SUCCESS : FAILED );
    }

    /**
     * 分页获取所有教师
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/teacher/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Teacher> list = teacherService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, list );
    }

    /**
     * 获取指定工作组里的所有教师
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/teacher/{groupId}", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId) {
        List<Teacher> list1 = teacherService.getByGroupId(groupId);
        List<Teacher> list2 = teacherService.getInvitingByGroupId( groupId );
        list2.forEach( item -> item.setState( STATE_INVITING.toString() ));
        list1.forEach( item -> item.setState( STATE_INVITE_SUCCESS.toString() ));
        list2.addAll( list1 );
        return MessageFactory.message( SUCCESS, list2 );
    }

    /**
     * 获取指定教师的角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/teacher/{id}/role", method = RequestMethod.GET)
    public Object getRole(@PathVariable int id) {
        List<Role> res = roleService.findTeacher( id );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 添加指定角色到指定教师
     * @param id
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/teacher/role/{id}/{roleId}", method = RequestMethod.POST)
    public Object addRole(@PathVariable int id, @PathVariable int roleId) {
        boolean flag = roleService.addTeacher( id, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 删除指定教师的指定角色
     * @param id
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/teacher/role/{id}/{roleId}", method = RequestMethod.DELETE)
    public Object deleteRole(@PathVariable int id, @PathVariable int roleId) {
        boolean flag = roleService.deleteTeacher( id, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    @RequestMapping(value = "/teacher/role/lead", method = RequestMethod.GET)
    public Object getLeadTeacherList() {
        List<Teacher> res = teacherService.getByRole( TEACHER_TYPE.TEACHER_LEAD.getVal() );
        return MessageFactory.message( SUCCESS, res );
    }

    @RequestMapping(value = "/teacher/search/{key}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable String key, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Teacher> res = teacherService.find( new Page<>( pageNum, pageSize ), key );
        return MessageFactory.message( SUCCESS, res );
    }
}
