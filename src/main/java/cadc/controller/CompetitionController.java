package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Competition;
import cadc.entity.Teacher;
import cadc.service.CompetitionService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param competition
     * @return
     */
    @RequestMapping(value = "/competition", method = RequestMethod.POST)
    public Object save(@RequestBody Competition competition) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        boolean flag = competitionService.insertCompetition( competition );
        return MessageFactory.message( flag ? SUCCESS : FAILED, "" );
    }
}
