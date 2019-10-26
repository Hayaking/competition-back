package cadc.service;

import cadc.bean.message.STATE;
import cadc.entity.Progress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface ProgressService extends IService<Progress> {
    List<Progress> getEnterNoEnd();

    List<Progress> getStartNoEnd();

    boolean setEnterState(int id, STATE state);

    boolean setStartState(int id, STATE state);
}
