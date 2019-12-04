package cadc.service.impl;

import cadc.entity.StudentGroupLog;
import cadc.mapper.StudentGroupLogMapper;
import cadc.service.StudentGroupLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class StudentGroupLogServiceImpl extends ServiceImpl<StudentGroupLogMapper, StudentGroupLog> implements StudentGroupLogService {
}
