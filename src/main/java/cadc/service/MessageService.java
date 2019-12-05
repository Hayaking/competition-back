package cadc.service;

import cadc.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public interface MessageService extends IService<Message> {

    Map<String, Object> getMessage(Subject subject);

    List<Message> getList(String toId);

    List<Message> getSystemMessageList(String toId);

    List<Message> getInviteMessageList(String toId);

}
