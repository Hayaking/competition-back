package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Teacher;
import cadc.entity.WorkLoad;
import cadc.service.WorkLoadService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haya
 */
@Log4j2
@RestController
public class WorkLoadController {
    @Autowired
    private WorkLoadService workLoadService;

    @GetMapping(value = "/workload/{pageNum}/{pageSize}")
    public Object getWorkLoad(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Teacher leadTeacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        IPage<WorkLoad> res = workLoadService.getWorkLoad( new Page<>( pageNum, pageSize ), leadTeacher.getId() );
        return MessageFactory.message( res );
    }
}
