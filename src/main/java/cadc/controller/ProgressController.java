package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.service.ProcessService;
import cadc.service.ProgressService;
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
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    @RequestMapping(value = "/progress/competition/{competitionId}", method = RequestMethod.GET)
    public Object create( @PathVariable int competitionId) {
        List<Progress> res = progressService.getByCompetitionId( competitionId );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public Object update( @RequestBody Progress progress) {
        log.warn( progress );
        Progress obj = progressService.getById( progress.getId() );
        obj.setEnterState( progress.getEnterState() );
        obj.setStartState( progress.getStartState() );
        boolean flag = obj.insertOrUpdate();
        return MessageFactory.message( flag );
    }
}
