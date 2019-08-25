package cadc.service.impl;

import cadc.entity.StudentGroup;
import cadc.mapper.StudentGroupMapper;
import cadc.service.StudentGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class StudentGroupServiceImpl extends ServiceImpl<StudentGroupMapper, StudentGroup> implements StudentGroupService {
}
