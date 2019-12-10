package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Works;
import cadc.service.WorksService;
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
public class WorksController {
    @Autowired
    private WorksService worksService;

    @RequestMapping(value = "/works", method = RequestMethod.POST)
    public Object addRole(@RequestBody Works works) {
        boolean flag = worksService.save( works );
        return MessageFactory.message( flag ? SUCCESS : FAILED, works.getId() );
    }

    @RequestMapping(value = "/works/group/{groupId}", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId) {
        List<Works> list = worksService.getByGroupId( groupId );
        return MessageFactory.message( list );
    }

    @GetMapping(value = "/works/{id}")
    public Object getByGroupId(@PathVariable Integer id) {
        Works res = worksService.getById( id );
        return MessageFactory.message( res );
    }
}
