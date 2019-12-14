package cadc.controller;

import cadc.bean.holder.ResultHolder;
import cadc.bean.message.MessageFactory;
import cadc.entity.Certificate;
import cadc.entity.Price;
import cadc.entity.Student;
import cadc.service.PriceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class PriceController {
    @Autowired
    private PriceService priceService;

    /**
     * 学生 上传比赛结果
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

    @GetMapping(value = "/price/student/{pageNum}/{pageSize}")
    public Object getByStudent(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        IPage<Price> res = priceService.getStudentPricePage( new Page<>( pageNum, pageSize ), student.getId() );
        return MessageFactory.message( res );
    }

    @GetMapping(value = "/price/recently")
    public Object getRecentlyPrice() {
        List<Price> res = priceService.getRecentlyPrice();
        return MessageFactory.message( res );
    }


}
