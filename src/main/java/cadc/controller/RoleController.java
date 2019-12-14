package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Role;
import cadc.entity.Teacher;
import cadc.service.RoleService;
import cadc.service.StudentService;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public Object getAll() {
        return MessageFactory.message( SUCCESS, roleService.list() );
    }

    @GetMapping(value = "/role/list/self")
    public Object getSelfRoleList() {
        Subject subject = SecurityUtils.getSubject();
        List<Role> res = roleService.getSelfRoleList( subject );
        return MessageFactory.message( res );
    }

    @GetMapping(value = "/role/{id}/user/{pageNum}/{pageSize}")
    public Object getUserPageByRole(@PathVariable Integer id, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        if (id == 5) {
            return MessageFactory.message( studentService.findAll( new Page<>( pageNum, pageSize ) ) );
        }
        IPage<Teacher> res = teacherService.getPageByRole( new Page<>( pageNum, pageSize ), id );
        return MessageFactory.message(res);
    }
}
