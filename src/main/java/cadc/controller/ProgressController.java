package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.JoinInProgress;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.service.JoinInProgressService;
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
    @Autowired
    private JoinInProgressService joinInProgressService;

    @RequestMapping(value = "/progress/list/competition/{competitionId}", method = RequestMethod.GET)
    public Object create( @PathVariable int competitionId) {
        List<Progress> res = progressService.getByCompetitionId( competitionId );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public Object update( @RequestBody Progress progress) {
        Progress obj = progressService.getById( progress.getId() );
        if (progress.getIsScanEnterState()) {
            obj.setIsScanStartState( true );
        }else{
            obj.setIsScanEnterState( false );
            obj.setEnterState( progress.getEnterState() );
        }
        if (progress.getIsScanStartState()) {
            obj.setIsScanStartState( true );
        } else {
            obj.setIsScanStartState( false );
            obj.setStartState( progress.getStartState() );
        }
        boolean flag = obj.insertOrUpdate();
        return MessageFactory.message( flag );
    }

    /**
     * 获取join 拥有的progress
     * @param joinId
     * @return
     */
    @GetMapping(value = "/progress/list/join/{joinId}")
    public Object getJoinProgress(@PathVariable int joinId) {
        List<JoinInProgress> list = joinInProgressService.getListByJoinId( joinId );
//        List<Progress> list = progressService.getListByJoinId( joinId );
        return MessageFactory.message( list );
    }
}
