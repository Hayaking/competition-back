package cadc.controller;

import cadc.bean.holder.EnterHolder;
import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.entity.JoinInProgress;
import cadc.entity.Student;
import cadc.service.JoinInProgressService;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class JoinController {
    @Autowired
    private JoinService joinService;
    @Autowired
    private JoinInProgressService joinInProgressService;

    /**
     * 学生创建参赛
     *
     * @param enterHolder
     * @return
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object addJoin(@RequestBody EnterHolder enterHolder) {
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        boolean flag = joinService.createJoin( student, enterHolder );
        return MessageFactory.message( flag );
    }

    @RequestMapping(value = "/join/{id}", method = RequestMethod.DELETE)
    public Object deleteJoin(@PathVariable int id) {
        boolean flag = joinService.deleteById( id );
        return MessageFactory.message( flag );
    }

    /**
     * 学生获取自己的参赛列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/join/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getJoinList(@PathVariable int pageNum, @PathVariable int pageSize) {
        Student stu = (Student) SecurityUtils.getSubject().getPrincipal();
        int id = stu.getId();
        IPage<Join> res = joinService.getByStudentId( new Page<>( pageNum, pageSize ), id );
        return MessageFactory.message( SUCCESS, res );
    }

    @RequestMapping(value = "/join/group/{groupId}", method = RequestMethod.GET)
    public Object getSimpleJoinListByGroupId(@PathVariable int groupId) {
        List<Join> list = joinService.getByGroupId( groupId );
        return MessageFactory.message( list );
    }

    /**
     * 工作组 根据progressId获取参赛列表 以及比赛结果
     *
     * @param progressId
     * @return
     */
    @GetMapping(value = "/join/progress/{progressId}/{pageNum}/{pageSize}")
    public Object getJoinListByProgressId(@PathVariable int progressId, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<JoinInProgress> res = joinInProgressService.getResultListByProgressId( new Page<>( pageNum, pageSize ), progressId );
        return MessageFactory.message( res );
    }

    /**
     * 审核学生提交的比赛结果
     *
     * @param reviewState
     * @param id
     * @param editState
     * @return
     */
    @PostMapping(value = "/join/progress/review/{id}/{reviewState}/{editState}")
    public Object reviewJoinInProgress(@PathVariable Integer reviewState, @PathVariable int id, @PathVariable Boolean editState) {
        JoinInProgress jip = joinInProgressService.getById( id );
        joinInProgressService.reviewResult( jip, reviewState, editState );
        // 设置审核状态
        jip.setReviewState( reviewState );
        // 设置可编辑状态
        jip.setIsEditable( editState );
        boolean flag = jip.insertOrUpdate();
        return MessageFactory.message( flag );
    }
}
