package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haya
 */
@Log4j2
@RestController
public class EnterController {
    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/enter/{competitionId}/list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getEnterList(@PathVariable int competitionId, @PathVariable int pageNum, @PathVariable int pageSize) {
        IPage<Join> res = joinService.getByCompetitionId( new Page<>( pageNum, pageSize ), competitionId );
        return MessageFactory.message( res );
    }

    @RequestMapping(value = "/enter/{competitionId}/export", method = RequestMethod.GET)
    public Object getEnterListExcel(@PathVariable int competitionId) {
        boolean flag = joinService.generateEnterListExcel( competitionId );
        return MessageFactory.message( flag );
    }

}
