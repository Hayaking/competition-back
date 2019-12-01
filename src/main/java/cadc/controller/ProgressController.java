package cadc.controller;

import cadc.bean.holder.ResultSummaryHolder;
import cadc.bean.message.MessageFactory;
import cadc.entity.JoinInProgress;
import cadc.entity.Progress;
import cadc.service.JoinInProgressService;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cadc.bean.PROGRESS_STATE.HAD_END;

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

    @GetMapping(value = "/progress/list/competition/{competitionId}")
    public Object getProgressByCompetitionId( @PathVariable int competitionId) {
        List<Progress> res = progressService.getByCompetitionId( competitionId );
        return MessageFactory.message( res );
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

    /**
     * 工作组 结束指定比赛阶段
     * @param id
     * @return
     */
    @PostMapping(value = "/progress/{id}/end")
    public Object endProgress( @PathVariable int id) {
        Progress progress = progressService.getById( id );
        progress.setStartState( HAD_END.getCode() );
        return MessageFactory.message( progress.insertOrUpdate() );
    }

    /**
     * 工作组 设置提交比赛结果状态
     * @param id
     * @return
     */
    @PostMapping(value = "/progress/{id}/result/{state}")
    public Object submitProgressResult(@PathVariable int id, @PathVariable Boolean state) {
        Progress progress = progressService.getById( id );
        progress.setIsSubmitResult( state );
        return MessageFactory.message( progress.insertOrUpdate() );
    }

    /**
     * 管理员 获取已提交比赛结果的 但没有被审核的
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/progress/review/list/{pageNum}/{pageSize}")
    public Object getNeedReviewProgress(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Progress> res = progressService.getNeedReviewList( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( res );
    }

    /**
     * 管理员 审核比赛结果
     *  IsReviewResult:
     *  -1 审核未通过
     *  0 未审核
     *  1 审核通过
     * @param id
     * @param state
     * @return
     */
    @PostMapping(value = "/progress/{id}/review/{state}")
    public Object reviewProgressResult(@PathVariable Integer id, @PathVariable Integer state) {
        Progress progress = progressService.getById( id );
        progress.setIsReviewResult( state );
        boolean res = progress.insertOrUpdate();
        return MessageFactory.message( res );
    }

    /**
     * 工作组更新 阶段信息
     * @param progress
     * @return
     */
    @PostMapping(value = "/progress/update")
    public Object update2(@RequestBody Progress progress) {
        boolean res = progress.insertOrUpdate();
        return MessageFactory.message( res );
    }

    /**
     * 工作组更改自动扫描状态
     * @param id
     * @param flag
     * @param name
     * @return
     */
    @PostMapping(value = "/progress/{id}/update/scan/{name}/{flag}")
    public Object update(@PathVariable Integer id, @PathVariable Boolean flag, @PathVariable String name) {
        Progress progress = progressService.getById( id );
        if ("start".equals( name )) {
            progress.setIsScanStartState( flag );
        } else if ("enter".equals( name )){
            progress.setIsScanEnterState( flag );
        }
        boolean res = progress.insertOrUpdate();
        return MessageFactory.message( res );
    }

    @PostMapping(value = "/progress/{id}/update/state/{name}/{state}")
    public Object update(@PathVariable Integer id, @PathVariable Integer state, @PathVariable String name) {
        Progress progress = progressService.getById( id );
        if ("start".equals( name )) {
            progress.setStartState( state );
        } else if ("enter".equals( name )) {
            progress.setEnterState( state );
        }
        boolean res = progress.insertOrUpdate();
        return MessageFactory.message( res );
    }

    /**
     * 工作组 提交比赛结果
     * @param progressId
     * @param holder
     * @return
     */
    @PostMapping(value = "/progress/{progressId}/result")
    public Object submitResult(@PathVariable Integer progressId, @RequestBody ResultSummaryHolder holder) {
        boolean res = progressService.generateResultWord( progressId, holder );
        return MessageFactory.message( res );
    }
}
