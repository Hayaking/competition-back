package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.entity.Works;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class JoinConntroller {

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object addRole(@RequestBody Join join) {
        boolean flag = join.insert();
        return MessageFactory.message( flag ? SUCCESS : FAILED);
    }
}
