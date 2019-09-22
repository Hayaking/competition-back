package cadc.service.impl;

import cadc.bean.message.STATE;
import cadc.entity.Student;
import cadc.entity.StudentInGroup;
import cadc.entity.Teacher;
import cadc.mapper.StudentGroupMapper;
import cadc.mapper.StudentInGroupMapper;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.MessageService;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Results;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private TeacherGroupMapper teacherGroupMapper;

    @Resource
    private StudentGroupMapper studentGroupMapper;

    private Map<String, Object> getTeacherMessage(int id) {
        Map<String, Object> map = new HashMap<>(5);
        map.put( "teacher_group", teacherGroupMapper.getInvitingByTeacherId( id ) );
        return map;
    }

    private Map<String, Object> getStudentMessage(int id) {
        Map<String, Object> map = new HashMap<>(5);
        map.put( "student_group", studentGroupMapper.getInvitingByStudentId( id ) );
        return map;
    }

    @Override
    public Map<String, Object> getMessage(Subject subject) {
        Object principal = subject.getPrincipal();
        if (principal instanceof Student) {
            return getStudentMessage( ((Student) principal).getId() );
        } else if (principal instanceof Teacher) {
            return getTeacherMessage( ((Teacher) principal).getId() );
        }
        return null;
    }

}
