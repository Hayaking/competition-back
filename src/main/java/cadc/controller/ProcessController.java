package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.service.ProcessService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class ProcessController {


    @RequestMapping(value = "/progress/list", method = RequestMethod.POST)
    public Object create(@RequestBody List<Progress> progresses) {

        return MessageFactory.message( true );
    }
}
