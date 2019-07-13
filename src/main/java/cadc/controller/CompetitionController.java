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

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
@Log4j2
@RestController
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    /**
     * 接收前端传来的competition对象
     *
     * @param competition
     * @return
     */
    @RequestMapping(value = "/competition", method = RequestMethod.POST)
    public Object save(@RequestBody Competition competition) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        competition.setState( "申请中" );
        competition.setCreater( teacher.getAccount() );
        boolean flag = competitionService.insertCompetition( competition );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }

    @RequestMapping(value = "/competition/{pageNum}/{pageSize}/{groupId}", method = RequestMethod.GET)
    public Object getByGroupId(@PathVariable int groupId, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Competition> res = competitionService.findByGroupId( new Page<>( pageNum, pageSize ), groupId );
        return MessageFactory.message( SUCCESS, res );
    }
}
