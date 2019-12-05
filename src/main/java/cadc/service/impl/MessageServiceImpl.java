package cadc.service.impl;

import cadc.entity.Message;
import cadc.entity.Student;
import cadc.entity.Teacher;
import cadc.mapper.MessageMapper;
import cadc.mapper.StudentGroupMapper;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;
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

    @Override
    public List<Message> getList(String toId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq( "to", toId );
        return messageMapper.selectList( wrapper );
    }

    @Override
    public List<Message> getSystemMessageList(String toId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq( "`to`", toId ).eq( "code", 0 );
        return messageMapper.selectList( wrapper );
    }

    @Override
    public List<Message> getInviteMessageList(String toId) {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.eq( "`to`", toId ).eq( "code", 1 );
        return messageMapper.selectList( wrapper );
    }

}
