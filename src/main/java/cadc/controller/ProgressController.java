package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Process;
import cadc.service.ProcessService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author haya
 */
@Log4j2
@RestController
public class ProgressController {
    @Autowired
    private ProcessService processService;

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public Object create(@RequestBody Process process) {
        boolean flag = process.insert();
        return MessageFactory.message( flag );
    }

}
