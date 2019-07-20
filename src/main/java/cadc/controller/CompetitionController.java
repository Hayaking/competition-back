package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Competition;
import cadc.entity.Teacher;
import cadc.service.CompetitionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cadc.bean.message.STATE.*;

/**
 * @author haya
 */
@Log4j2
@RestController
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    /**
     * 申请比赛立项
     *
     * @param competition
     * @return
     */
    @RequestMapping(value = "/competition", method = RequestMethod.POST)
    public Object save(@RequestBody Competition competition) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        competition.setState( STATE_COMPETITION_APPLYING.toString() );
        competition.setEnterState( STATE_COMPETITION_NOT_START.toString() );
        competition.setCreater( teacher.getAccount() );
        boolean flag = competitionService.insertCompetition( competition );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    /**
     * 根据组id分页查询
     * @param groupId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/competition/{pageNum}/{pageSize}/{groupId}", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.findByGroupId( new Page<>( pageNum, pageSize ), groupId );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 分页查询全部
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/competition/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAll(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.findAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 设置审核状态
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping(value = "/competition/state/{id}/{flag}", method = RequestMethod.POST)
    public Object setState(@PathVariable int id, @PathVariable boolean flag) {
        // true同意, false拒绝
        if (flag) {
            competitionService.setState( id, STATE_COMPETITION_AGREE.toString() );
        } else {
            competitionService.setState( id, STATE_COMPETITION_REFUSE.toString() );
        }
        return MessageFactory.message( SUCCESS, "" );
    }

    /**
     * 设置比赛开始状态
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping(value = "/competition/enterState/{id}/{flag}", method = RequestMethod.POST)
    public Object setEnterState(@PathVariable int id, @PathVariable boolean flag) {
        // true开始, false结束
        if (flag) {
            competitionService.setEnterState( id, STATE_COMPETITION_HAD_START.toString() );
        } else {
            competitionService.setEnterState( id, STATE_COMPETITION_END.toString() );
        }
        return MessageFactory.message( SUCCESS, "" );
    }
}
