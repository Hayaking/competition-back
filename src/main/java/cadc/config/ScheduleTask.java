package cadc.config;

import cadc.bean.PRROGRESS_STATE;
import cadc.entity.Progress;
import cadc.service.ProgressService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * @author haya
 */
@Log4j2
@Component
@EnableScheduling
public class ScheduleTask {
    @Autowired
    private ProgressService progressService;

    @Scheduled(fixedRate=60000)
    private void scanEnterState() {
        log.info( "检查报名状态开始" );
        List<Progress> noStartList = progressService.getEnterNoStart();
        List<Progress> hadStartList = progressService.getEnterHadStart();
        Date now = new Date();
        noStartList.forEach( item ->{
            if (now.getTime() >= item.getEnterStartTime().getTime()) {
                progressService.setEnterState( item.getId(), PRROGRESS_STATE.HAD_START );
            }
        } );
        hadStartList.forEach( item ->{
            if (now.getTime() >= item.getEnterEndTime().getTime()) {
                progressService.setEnterState( item.getId(), PRROGRESS_STATE.HAD_END );
            }
        } );
        log.info( "检查报名状态完毕" );
    }

    @Scheduled(fixedRate=60000)
    private void scanStartTime() {
        log.info( "检查开始状态开始" );
        List<Progress> noStartList = progressService.getStartNoStart();
        List<Progress> hadStartList = progressService.getStartHadStart();
        Date now = new Date();
        noStartList.forEach( item ->{
            if (now.getTime() >= item.getStartTime().getTime()) {
                progressService.setStartState( item.getId(), PRROGRESS_STATE.HAD_START );
            }
        } );
        hadStartList.forEach( item ->{
            if (now.getTime() >= item.getEndTime().getTime()) {
                progressService.setStartState( item.getId(), PRROGRESS_STATE.WAIT_END );
            }
        } );
        log.info( "检查开始状态结束" );
    }
}
