package cadc.service.impl;

import cadc.entity.Teacher;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.MessageService;
import cadc.service.TeacherGroupService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haya
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private TeacherGroupMapper teacherGroupMapper;

    @Override
    public Map<String, Object> getTeacherMessage(Subject subject) {
        Map<String, Object> map = new HashMap<>(5);
        String account = ((Teacher) subject.getPrincipal()).getAccount();
        if (subject.hasRole( "教师组员" )) {
            map.put( "group", teacherGroupMapper.getInvitingByTeacherId( account ) );
        }
        if (subject.hasRole( "指导教师" )){
            map.put( "join", null );
        }
        return map;
    }

    @Override
    public Map<String, Object> getStudentMessage(String account) {
        return null;
    }

}
