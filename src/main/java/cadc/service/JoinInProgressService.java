package cadc.service;

import cadc.entity.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author haya
 */
public interface JoinInProgressService extends IService<JoinInProgress> {
    Boolean promotion(Integer joinInProgressId, Boolean flag);

    List<JoinInProgress> getListByJoinId(int joinId);

    Page<JoinInProgress> getEnterPage(Page<JoinInProgress> page, int competitionId, int progressId);

    boolean setEnterState(int inProgressId, boolean flag);

    Page<JoinInProgress>getResultListByProgressId(Page<JoinInProgress> page, int progressId);
}
