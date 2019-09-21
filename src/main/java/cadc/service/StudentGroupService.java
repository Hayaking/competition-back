package cadc.service;

import cadc.entity.StudentGroup;
import cadc.entity.StudentInGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface StudentGroupService extends IService<StudentGroup> {

    IPage<StudentGroup> getByStudentId(Page<StudentGroup> page, int id);
}
