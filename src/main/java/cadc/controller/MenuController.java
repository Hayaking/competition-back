package cadc.controller;

import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.service.MenuService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haya
 */
@Log4j2
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 接收前端传来的type
     * 返回路由菜单
     * @param type
     * @return
     */
    @RequestMapping(value = "/menu/{type}", method = RequestMethod.GET)
    public Object menu( @PathVariable String type) {
        Subject subject = SecurityUtils.getSubject();
        String account = null;
        if ("student".equals( type )) {
            Student stu = (Student) subject.getPrincipal();
            account = stu.getAccount();
        } else {
            Teacher teacher = (Teacher) subject.getPrincipal();
            account = teacher.getAccount();
        }
        return menuService.getMenu( account ,type);
    }
}
