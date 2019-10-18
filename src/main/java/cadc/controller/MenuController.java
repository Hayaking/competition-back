package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.*;
import cadc.service.MenuService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 返回路由菜单
     * @return
     */
//    @RequestMapping(value = "/menu", method = RequestMethod.GET)
//    public Object menu() {
//        Subject subject = SecurityUtils.getSubject();
//        if (subject == null) {
//            return MessageFactory.message( false );
//        }
//        Object obj = subject.getPrincipal();
//        if (obj instanceof Student) {
//            Student stu = (Student) obj;
//            return MessageFactory.message(  menuService.getMenu( stu.getId(), "student" ) );
//        } else if (obj instanceof Teacher) {
//            Teacher teacher = (Teacher) obj;
//            return MessageFactory.message(  menuService.getMenu(  teacher.getId(), "teacher" ) );
//        } else {
//            return MessageFactory.message( false );
//        }
//    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public Object getMenu() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return MessageFactory.message( false );
        }
        List<Menu1> res = menuService.getMenuByUser( subject.getPrincipal() );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/menu/all", method = RequestMethod.GET)
    public Object getAll( ) {
        return menuService.getAll();
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public Object save(@RequestBody Menu menu) {
        System.out.println(menu);
        Meta meta = menu.getMeta();
        boolean flag1 = meta.insertOrUpdate();
        menu.setMetaId( meta.getId() );
        boolean flag2 = menu.insertOrUpdate();
        return MessageFactory.message( flag1 && flag2 ? SUCCESS : FAILED );
    }
}
