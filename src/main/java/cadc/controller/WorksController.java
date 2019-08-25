package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Works;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class WorksController {
    @RequestMapping(value = "/works", method = RequestMethod.POST)
    public Object addRole(@RequestBody Works works) {
        boolean flag = works.insert();
        return MessageFactory.message( flag ? SUCCESS : FAILED, works.getId() );
    }
}
