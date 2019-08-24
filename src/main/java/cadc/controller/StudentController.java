package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Role;
import cadc.entity.Student;
import cadc.service.RoleService;
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
    @Autowired
    private RoleService roleService;

    /**
     * 更新学生信息
     * @param student
     * @return
     */
    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    public Object update(@RequestBody Student student) {
        boolean flag = studentService.updateById( student );
        log.info( student );
        return MessageFactory.message( flag?SUCCESS:FAILED, "" );
    }

    /**
     * 根据账号查看学僧是否存在
     * @return
     */
    @RequestMapping(value = "/student/{account}", method = RequestMethod.GET)
    public Object get(@PathVariable String account) {
        Student res = studentService.getById( account );
        return MessageFactory.message( res != null ? SUCCESS : FAILED );
    }

    /**
     * 分页获取所有学生
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/student/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Student> res = studentService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 获取指定学生的角色
     * @param account
     * @return
     */
    @RequestMapping(value = "/student/role/{account}", method = RequestMethod.GET)
    public Object getRole(@PathVariable String account) {
        List<Role> res = roleService.findStudent( account );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 添加角色到指定学生
     * @param account
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/student/role/{account}/{roleId}", method = RequestMethod.POST)
    public Object addRole(@PathVariable String account, @PathVariable int roleId) {
        boolean flag = roleService.addStudent( account, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 删除指定学生的指定角色
     * @param account
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/student/role/{account}/{roleId}", method = RequestMethod.DELETE)
    public Object deleteRole(@PathVariable String account, @PathVariable int roleId) {
        boolean flag = roleService.deleteStudent( account, roleId );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }
}
