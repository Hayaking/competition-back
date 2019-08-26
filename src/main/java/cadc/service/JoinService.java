package cadc.service;

import cadc.entity.Join;
import cadc.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface JoinService extends IService<Join> {
    IPage<Join> getByStudentAccount(Page<Join> page, String account);
}
