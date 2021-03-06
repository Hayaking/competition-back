package cadc.service;

import cadc.entity.StudentGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface StudentGroupService extends IService<StudentGroup> {

    IPage<StudentGroup> getPageByStudentId(Page<StudentGroup> page, int id);

    List<StudentGroup> getListByStudentId(int id);
}
