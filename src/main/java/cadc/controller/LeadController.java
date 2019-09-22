package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Join;
import cadc.entity.Teacher;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
public class LeadController {

    @Autowired
    private JoinService joinService;

    @RequestMapping(path = "/lead/apply/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public Object getApply(@PathVariable int pageSize, @PathVariable int pageNum) {
        Subject subject = SecurityUtils.getSubject();
        Teacher teacher = (Teacher) subject.getPrincipal();
        IPage<Join> res = joinService.getByLead( new Page<>( pageNum, pageSize ), teacher.getId() );
        return MessageFactory.message( res != null, res );
    }

    @RequestMapping(path = "/lead/review/{flag}/{joinId}", method = RequestMethod.POST)
    public Object getApply(@PathVariable Boolean flag, @PathVariable int joinId) {
        Subject subject = SecurityUtils.getSubject();
        Teacher teacher = (Teacher) subject.getPrincipal();
        flag = joinService.setApplyState( flag, joinId, teacher.getId() );
        return MessageFactory.message( flag );
    }
}
