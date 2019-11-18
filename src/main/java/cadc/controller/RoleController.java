package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.entity.Role;
import cadc.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
