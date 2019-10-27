package cadc.service;

import cadc.bean.message.STATE;
import cadc.entity.Process;
import cadc.entity.Progress;
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

    boolean setEnterState(int id, STATE state);

    boolean setStartState(int id, STATE state);

    List<Progress> getByCompetitionId(int competitionId);
}
