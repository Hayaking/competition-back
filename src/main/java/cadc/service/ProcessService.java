package cadc.service;

import cadc.entity.Process;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface ProcessService extends IService<Process> {

    Page<Process> getByJoinId(Page<Process> page, int competitionId);

}
