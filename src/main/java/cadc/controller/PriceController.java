package cadc.controller;

import cadc.bean.holder.ResultHolder;
import cadc.bean.message.MessageFactory;
import cadc.entity.Certificate;
import cadc.entity.Price;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cadc.bean.message.STATE.STATE_APPLYING;

/**
 * @author haya
 */
@Log4j2
@RestController
public class PriceController {

    @RequestMapping(value = "/price", method = RequestMethod.POST)
    public Object price(@RequestBody ResultHolder resultHolder) {
        log.warn( resultHolder );
        Price price = resultHolder.getPrice();
        price.setPriceState( STATE_APPLYING.toString() );
        boolean flag = price.insert();
        Certificate certificate = resultHolder.getCertificate();
        certificate.setPriceId( price.getId() );
        certificate.insert();
        return MessageFactory.message( true );
    }
}
