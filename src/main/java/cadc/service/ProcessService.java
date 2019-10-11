package cadc.service;

import cadc.entity.Process;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface ProcessService extends IService<Process> {

    Page<Process> getByJoinId(Page<Process> page, int competitionId);

}
