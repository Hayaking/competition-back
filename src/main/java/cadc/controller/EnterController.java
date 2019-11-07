package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.entity.JoinInProgress;
import cadc.service.JoinInProgressService;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author haya
 */
@Log4j2
@RestController
public class EnterController {
    @Autowired
    private JoinService joinService;
    @Autowired
    private JoinInProgressService joinInProgressService;

    /**
     * 获取报名列表
     *
     * @param competitionId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/enter/{competitionId}/list/{pageNum}/{pageSize}")
    public Object getEnterList(@PathVariable int competitionId, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Join> res = joinService.getByCompetitionId( new Page<>( pageNum, pageSize ), competitionId );
        return MessageFactory.message( res );
    }

    @GetMapping(value = "/enter/{competitionId}/{progressId}/{pageNum}/{pageSize}")
    public Object getEnterList(@PathVariable int competitionId, @PathVariable int progressId, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Join> res = joinService.getEnterList( new Page<>( pageNum, pageSize ), competitionId, progressId );
        return MessageFactory.message( res );
    }

    /**
     * 审核join的enterState
     *
     * @param joinId
     * @param flag
     * @return
     */
    @PostMapping(value = "/enter/{joinId}/{flag}")
    public Object reviewEnter(@PathVariable int joinId, @PathVariable boolean flag) {
        boolean res = joinService.setEnterState( flag, joinId );
        return MessageFactory.message( res );
    }

    @PostMapping(value = "/enter/promotion/{joinInProgressId}/{flag}")
    public Object promotion(@PathVariable Integer joinInProgressId, @PathVariable Boolean flag) {
        Boolean res = joinInProgressService.promotion( joinInProgressId, flag );
        return MessageFactory.message( res );
    }
}
