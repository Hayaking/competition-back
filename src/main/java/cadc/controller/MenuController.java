package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.entity.Menu;
import cadc.entity.Meta;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.service.MenuService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 接收前端传来的type 返回路由菜单
     * @param type
     * @return
     */
    @RequestMapping(value = "/menu/{type}", method = RequestMethod.GET)
    public Object menu( @PathVariable String type) {
        Subject subject = SecurityUtils.getSubject();
        int id;
        if ("student".equals( type )) {
            Student stu = (Student) subject.getPrincipal();
            id = stu.getId();
        } else {
            Teacher teacher = (Teacher) subject.getPrincipal();
            log.warn( teacher );
            id = teacher.getId();
        }
        return menuService.getMenu( id ,type);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
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
//        return MessageFactory.message( SUCCESS  );
    }
}
