package cadc.service;

import cadc.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface StudentService extends IService<Student> {
    /**
     * 根据帐号密码找到学生
     * @param account
     * @param password
     * @return
     */
    Student find(String account,String password);

    /**
     * 增加学生
     * @param student
     * @return
     */
    boolean insert(Student student);

    IPage<Student> findAll(Page<Student> page);
}
