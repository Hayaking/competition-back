package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Menu1;
import cadc.service.Menu1Service;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class MenuController {
    @Autowired
    private Menu1Service menu1Service;


    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public Object getMenu() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return MessageFactory.message( false );
        }
        List<Menu1> res = menu1Service.getMenuByUser( subject.getPrincipal() );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/menu/all", method = RequestMethod.GET)
    public Object getAll( ) {
        return MessageFactory.message( true );
//        return menuService.getAll();
    }

//    @RequestMapping(value = "/menu", method = RequestMethod.POST)
//    public Object save(@RequestBody Menu menu) {
//        System.out.println(menu);
//        Meta meta = menu.getMeta();
//        boolean flag1 = meta.insertOrUpdate();
//        menu.setMetaId( meta.getId() );
//        boolean flag2 = menu.insertOrUpdate();
//        return MessageFactory.message( flag1 && flag2 ? SUCCESS : FAILED );
//    }
}
