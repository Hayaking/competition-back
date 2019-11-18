package cadc.controller;

import cadc.bean.holder.ResultHolder;
import cadc.bean.message.MessageFactory;
import cadc.entity.Certificate;
import cadc.entity.Price;
import cadc.service.PriceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cadc.bean.message.STATE.STATE_APPLYING;

/**
 * @author haya
 */
@Log4j2
@RestController
public class PriceController {
    @Autowired
    private PriceService priceService;

    /**
     * 学生上传比赛结果
     * @param resultHolder
     * @return
     */
    @RequestMapping(value = "/price", method = RequestMethod.POST)
    public Object price(@RequestBody ResultHolder resultHolder) {
        Boolean isWinPrice = resultHolder.getIsWinThePrice();
        Price price = resultHolder.getPrice();
        Certificate certificate = resultHolder.getCertificate();
        priceService.create( isWinPrice, price, certificate );
        return MessageFactory.message( true );
    }

    @GetMapping(value = "/price/joinInProgress/{joinInProgressId}")
    public Object getByJoinInProgress(@PathVariable int joinInProgressId) {
        Price price = priceService.getByInProgressId( joinInProgressId );
        return MessageFactory.message( price );
    }
}
