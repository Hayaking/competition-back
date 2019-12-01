package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.service.ProcessService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class ProcessController {

    @Autowired
    private ProcessService processService;

    /**
     * 工作组 上传比赛过程
     * @param process
     * @return
     */
    @PostMapping(value = "/process")
    public Object create(@RequestBody Process process) {
        boolean flag = process.insert();
        return MessageFactory.message( flag );
    }

    /**
     * 工作组 分页获取比赛过程
     * @param competitionId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/process/{competitionId}/{pageNum}/{pageSize}")
    public Object getPage(@PathVariable int competitionId, @PathVariable int pageNum, @PathVariable int pageSize) {
        Page<Process> res = processService.getByCompetitionId( new Page<>( pageNum, pageSize ), competitionId );
        return MessageFactory.message( res );
    }

    @GetMapping(value = "/process/{progressId}")
    public Object getList(@PathVariable String progressId) {
        List<Process> res = processService.getListByProcessId( progressId );
        return MessageFactory.message( res );
    }
}
