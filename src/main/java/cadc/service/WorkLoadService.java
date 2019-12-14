package cadc.service;

import cadc.entity.WorkLoad;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface WorkLoadService extends IService<WorkLoad> {
    IPage<WorkLoad> getWorkLoad(Page<WorkLoad> page, int id);
}
