package cadc.service;

import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public interface MessageService {
    /**
     * 查询 工作组邀请信息或比赛指导邀请
     * @param subject
     * @return
     */
    Map<String, Object> getTeacherMessage(Subject subject);

    Map<String, Object> getStudentMessage(String account);
}
