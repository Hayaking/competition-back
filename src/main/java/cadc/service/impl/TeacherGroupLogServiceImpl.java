package cadc.service.impl;

import cadc.entity.TeacherGroupLog;
import cadc.mapper.TeacherGroupLogMapper;
import cadc.service.TeacherGroupLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class TeacherGroupLogServiceImpl extends ServiceImpl<TeacherGroupLogMapper, TeacherGroupLog> implements TeacherGroupLogService {
    @Async
    @Override
    public boolean add(TeacherGroupLog teacherGroupLog) {
        return teacherGroupLog.insert();
    }
}
