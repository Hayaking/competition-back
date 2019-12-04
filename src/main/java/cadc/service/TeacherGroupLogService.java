package cadc.service;

import cadc.entity.TeacherGroupLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface TeacherGroupLogService extends IService<TeacherGroupLog> {
    boolean add(TeacherGroupLog teacherGroupLog);

}
