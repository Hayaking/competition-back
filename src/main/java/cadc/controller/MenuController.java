package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Menu1;
import cadc.service.Menu1Service;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 获取菜单
     *
     * @return
     */
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public Object getMenu() {
        Subject subject = SecurityUtils.getSubject();
        List<Menu1> res = menu1Service.getMenuByUser( subject.getPrincipal() );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/menu/all", method = RequestMethod.GET)
    public Object getAll() {
        List<Menu1> res = menu1Service.getAll();
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/menu/role/{roleId}", method = RequestMethod.GET)
    public Object getByRoleId(@PathVariable int roleId) {
        List<Menu1> res = menu1Service.getByRoleId( roleId );
        return MessageFactory.message( res );
    }

    /**
     * 设置用户提交的 menu1 menu2
     * @param roleId
     * @param menu1Id
     * @param menu2Id
     * @param flag
     * @return
     */
    @RequestMapping(value = "/menu/{roleId}/{menu1Id}/{menu2Id}/{flag}", method = RequestMethod.POST)
    public Object setRoleAndMenu(@PathVariable int roleId, @PathVariable int menu1Id, @PathVariable int menu2Id, @PathVariable boolean flag) {
        boolean res = menu1Service.setRoleAndMenu( roleId, menu1Id, menu2Id, flag );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/menu/{roleId}/{menu1Id}/{flag}", method = RequestMethod.POST)
    public Object setRoleAndMenu(@PathVariable int roleId, @PathVariable int menu1Id, @PathVariable boolean flag) {
        boolean res = menu1Service.setRoleAndMenu( roleId, menu1Id, flag );
        return MessageFactory.message( res );
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
