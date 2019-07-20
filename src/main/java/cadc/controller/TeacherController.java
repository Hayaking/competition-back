package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Role;
import cadc.entity.Teacher;
import cadc.service.RoleService;
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
     * @param account
     * @return
     */
    @RequestMapping(value = "/teacher/role/{account}", method = RequestMethod.GET)
    public Object getRole(@PathVariable String account) {
        List<Role> res = roleService.findTeacher( account );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 添加指定角色到指定教师
     * @param account
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/teacher/role/{account}/{roleId}", method = RequestMethod.POST)
    public Object addRole(@PathVariable String account, @PathVariable int roleId) {
        boolean flag = roleService.addTeacher( account, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 删除指定教师的指定角色
     * @param account
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/teacher/role/{account}/{roleId}", method = RequestMethod.DELETE)
    public Object deleteRole(@PathVariable String account, @PathVariable int roleId) {
        boolean flag = roleService.deleteTeacher( account, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }
}
