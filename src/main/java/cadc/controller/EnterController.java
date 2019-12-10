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
     * 工作组 获取报名列表
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
    /**
     * 工作组 获取报名列表
     *
     * @param competitionId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/enter/{competitionId}/{progressId}/{pageNum}/{pageSize}")
    public Object getEnterList(@PathVariable int competitionId, @PathVariable int progressId, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<JoinInProgress> res = joinInProgressService.getEnterPage( new Page<>( pageNum, pageSize ), competitionId, progressId );
//        IPage<Join> res = joinService.getEnterList( new Page<>( pageNum, pageSize ), competitionId, progressId );
        return MessageFactory.message( res );
    }

    /**
     * 工作组 审核参赛的报名状态
     *
     * @param inProgressId
     * @param flag
     * @return
     */
    @PostMapping(value = "/enter/{inProgressId}/{flag}")
    public Object reviewEnter(@PathVariable int inProgressId, @PathVariable Integer flag) {
        boolean res = joinInProgressService.setEnterState( inProgressId, flag );
        return MessageFactory.message( res );
    }

    /**
     * 审核晋级
     * @param joinInProgressId
     * @param flag
     * @return
     */
    @PostMapping(value = "/enter/promotion/{joinInProgressId}/{flag}")
    public Object promotion(@PathVariable Integer joinInProgressId, @PathVariable Boolean flag) {
        Boolean res = joinInProgressService.promotion( joinInProgressId, flag );
        return MessageFactory.message( res );
    }
}
