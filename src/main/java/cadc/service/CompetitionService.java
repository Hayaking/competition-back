package cadc.service;

import cadc.entity.Competition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public interface CompetitionService extends IService<Competition> {
    /**
     * 增加一个竞赛
     * @param competition
     * @return
     */
    boolean insertCompetition(Competition competition);

    Integer add(Competition competition);

    IPage<Competition> findByGroupId(Page<Competition> page, int groupId);

    IPage<Competition> findAll(Page<Competition> page);

    boolean setState(int id, String state);

    boolean setStartState(int id, String state);

    List<Competition> getEnterNoEnd();

    List<Competition> getStartNoEnd();

    List<Competition> get5ByType(int typeId);

    boolean setEnterState(int id, String state);

    String generateWord(int id);

    String generateWord(Competition competition);
}
