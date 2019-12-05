package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.TeacherGroupLog;
import cadc.service.TeacherGroupLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haya
 */
@Log4j2
@RestController
public class TeacherGroupLogController {
    @Autowired
    private TeacherGroupLogService teacherGroupLogService;

    @GetMapping(value = "/teacherGroupLog/{groupId}/{pageNum}/{pageSize}")
    public Object get(@PathVariable Integer groupId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        IPage<TeacherGroupLog> page = teacherGroupLogService.getList( new Page<>( pageNum, pageSize ), groupId );
//        list.sort( (o1, o2) -> o2.getCreateTime().compareTo( o1.getCreateTime() ) );
        return MessageFactory.message( page );
    }

}
