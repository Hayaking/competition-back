package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.bean.message.STATE;
import cadc.service.TypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public Object get(@PathVariable String type) {
        List res = null;
        switch (type) {
            case "competition":
                res = typeService.findCompetitionType();
                break;
            case "cost":
                break;
            case "price":
                break;
            case "join":
                break;
            default:
                break;
        }
        return MessageFactory.message( SUCCESS, res );
    }
}
