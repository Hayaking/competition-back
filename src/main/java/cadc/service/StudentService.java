package cadc.service;

import cadc.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface StudentService extends IService<Student> {

    boolean sign(Student student);

    /**
     * 根据帐号密码找到学生
     * @param account
     * @param password
     * @return
     */
    Student find(String account,String password);

    Student find(String account);

    List<String> exist(List<String> list);

    /**
     * 增加学生
     * @param student
     * @return
     */
    boolean insert(Student student);

    /**
     * 分页获取所有学生
     *
     * @param page
     * @return
     */
    IPage<Student> findAll(Page<Student> page);

    IPage<Student> find(Page<Student> page, String key);

    /**
     * 根据账号查看学生存在否
     * @param account
     * @return
     */
    boolean isExistByAccount(String account);

}
