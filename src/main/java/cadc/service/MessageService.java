package cadc.service;

import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public interface MessageService {

    Map<String, Object> getMessage(Subject subject);

}
