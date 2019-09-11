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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
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

    @Override
    public IPage<Student> find(Page<Student> page, String key) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.like( "account", key ).or()
                .like( "stu_name", key );
        return studentMapper.selectPage( page, wrapper );
    }
}
