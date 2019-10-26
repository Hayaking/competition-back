package cadc.config;

import cadc.entity.Competition;
import cadc.entity.Progress;
import cadc.service.CompetitionService;
import cadc.service.ProgressService;
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
    private ProgressService progressService;

    @Scheduled(fixedRate=60000)
    private void scanEnterState() {
        log.warn( "检查报名状态" );
        List<Progress> list = progressService.getEnterNoEnd();
        Date now = new Date();
        list.parallelStream().forEach( item->{
            if (now.getTime() >= item.getEnterEndTime().getTime()) {
                progressService.setEnterState( item.getId(), STATE_END );
            } else if (now.getTime() >= item.getEnterStartTime().getTime()) {
                progressService.setEnterState( item.getId(), STATE_HAD_START );
            }
        });
    }
    @Scheduled(fixedRate=60000)
    private void scanStartTime() {
        List<Progress> list = progressService.getStartNoEnd();
        Date now = new Date();
        list.parallelStream().forEach( item -> {
            if (now.getTime() > item.getEndTime().getTime()) {
                progressService.setStartState( item.getId(), STATE_END );
            } else if (now.getTime() > item.getStartTime().getTime()) {
                progressService.setStartState( item.getId(), STATE_HAD_START );
            }
        } );
    }
}
