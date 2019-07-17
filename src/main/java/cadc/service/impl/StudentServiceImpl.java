package cadc.service.impl;

import cadc.entity.Student;
import cadc.entity.StudentGroup;
import cadc.entity.TeacherGroup;
import cadc.mapper.StudentGroupMapper;
import cadc.mapper.StudentMapper;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student find(String account, String password) {
        return studentMapper.find( account, password );
    }

    @Override
    public boolean insert(Student student) {
        int res = studentMapper.insertStudent( student.getAccount(),student.getPassword() );
        return res > 0;
    }

    @Override
    public IPage<Student> findAll(Page<Student> page) {
        return studentMapper.selectPage( page, new QueryWrapper<>() );
    }
}
