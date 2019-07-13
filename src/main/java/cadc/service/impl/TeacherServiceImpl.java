package cadc.service.impl;

import cadc.entity.Teacher;
import cadc.mapper.TeacherMapper;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher find(String account, String password) {
        return teacherMapper.find( account, password );
    }

}
