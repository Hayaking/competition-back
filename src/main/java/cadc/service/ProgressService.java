package cadc.service;

import cadc.bean.PROGRESS_STATE;
import cadc.bean.holder.ResultSummaryHolder;
import cadc.entity.Progress;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface ProgressService extends IService<Progress> {
    List<Progress> getEnterNoStart();

    List<Progress> getEnterHadStart();

    List<Progress> getStartNoStart();

    List<Progress> getStartHadStart();

    boolean setEnterState(int id, PROGRESS_STATE state);

    boolean setStartState(int id, PROGRESS_STATE state);

    List<Progress> getByCompetitionId(int competitionId);

    List<Progress> getListByJoinId(int joinId);

    IPage<Progress> getNeedReviewList(Page<Progress> page);

    Progress getNeedReviewById(int id);

    boolean generateResultWord(Integer progressId, ResultSummaryHolder holder);
}
