package cadc.service;

import cadc.entity.TeacherGroupLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface TeacherGroupLogService extends IService<TeacherGroupLog> {
    boolean add(TeacherGroupLog teacherGroupLog);

    IPage<TeacherGroupLog> getList(Page<TeacherGroupLog> page, Integer groupId);

}
