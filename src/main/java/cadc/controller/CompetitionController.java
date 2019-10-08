package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Competition;
import cadc.entity.Teacher;
import cadc.service.CompetitionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param competition
     * @return
     */
    @RequestMapping(value = "/competition", method = RequestMethod.POST)
    public Object save(@RequestBody Competition competition) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        competition.setState( STATE_APPLYING.toString() );
        competition.setEnterState( STATE_NOT_START.toString() );
        competition.setStartState( STATE_NOT_START.toString() );
        competition.setCreator( teacher.getAccount() );
        int id = competitionService.add( competition );
        competition.setId( id );
        competitionService.generateWord( competition );
        return MessageFactory.message(  SUCCESS );
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/competition/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable int id) {
        boolean flag = competitionService.deleteById( id );
        return MessageFactory.message( flag ? SUCCESS : FAILED );
    }

    /**
     * 根据工作组id 分页查询竞赛
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
     * 获得5个
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/competition/rank/{typeId}", method = RequestMethod.GET)
    public Object get5ByType(@PathVariable int typeId) {
        List<Competition> res = competitionService.get5ByType( typeId );
        return MessageFactory.message( SUCCESS, res );
    }

    /**
     * 参赛时学生根据竞赛id得到竞赛
     * @param id
     * @return
     */
    @RequiresRoles( "学生" )
    @RequestMapping(value = "/competition/enter/{id}", method = RequestMethod.GET)
    public Object getEnterCompetition(@PathVariable int id) {
        Competition res = competitionService.getById( id );
        return MessageFactory.message( SUCCESS, res );
    }

    @RequestMapping(value = "/competition/{id}", method = RequestMethod.GET)
    public Object getCompetitionById(@PathVariable int id) {
        Competition res = competitionService.getById( id );
        return MessageFactory.message( res );
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
            competitionService.setState( id, STATE_AGREE.toString() );
        } else {
            competitionService.setState( id, STATE_REFUSE.toString() );
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
            competitionService.setEnterState( id, STATE_HAD_START.toString() );
        } else {
            competitionService.setEnterState( id, STATE_END.toString() );
        }
        return MessageFactory.message( SUCCESS, "" );
    }

    @RequestMapping(value = "/competition/judge/search/{key}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object searchJudge(@PathVariable String key, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.find(new Page<>(pageNum, pageSize), key);
        return MessageFactory.message(SUCCESS, res);
    }

    @RequestMapping(value = "/competition/pass/search/{key}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object searchPass(@PathVariable String key, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.findPassByKey(new Page<>(pageNum, pageSize), key);
        return MessageFactory.message(SUCCESS, res);
    }

    /**
     * 分获取所有审核通过的比赛
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/competition/pass/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getAllPass(@PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.findPassAll( new Page<>( pageNum, pageSize ) );
        return MessageFactory.message( SUCCESS, res );
    }
}
