package cadc.config;

import cadc.entity.Competition;
import cadc.service.CompetitionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static cadc.bean.message.STATE.STATE_END;
import static cadc.bean.message.STATE.STATE_HAD_START;

/**
 * @author haya
 */
@Log4j2
@Component
@Configuration
@EnableScheduling
public class ScheduleTask {
    @Autowired
    private CompetitionService competitionService;

    @Scheduled(fixedRate=60000)
    private void competitionEnterTime() {
        log.warn( "检查报名状态" );
        List<Competition> list = competitionService.getEnterNoEnd();
        Date now = new Date();
        for (Competition item : list) {
            // 结束
            if (now.getTime() > item.getEnterEndTime().getTime()) {
                competitionService.setEnterState( item.getId(), STATE_END.toString() );
            // 开始
            } else if (now.getTime() > item.getEnterStartTime().getTime()) {
                competitionService.setEnterState( item.getId(), STATE_HAD_START.toString() );
            }
        }
    }
    @Scheduled(fixedRate=60000)
    private void competitionStartTime() {
        List<Competition> list = competitionService.getStartNoEnd();
        Date now = new Date();
        for (Competition item : list) {
            if (now.getTime() > item.getEndTime().getTime()) {
                competitionService.setStartState( item.getId(), STATE_END.toString() );
            } else if (now.getTime() > item.getStartTime().getTime()) {
                competitionService.setStartState( item.getId(), STATE_HAD_START.toString() );
            }
        }
    }
}
