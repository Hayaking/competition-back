package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
