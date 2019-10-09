package cadc.service;

import cadc.entity.Competition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

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

    boolean deleteById(int id);

    IPage<Competition> findByGroupId(Page<Competition> page, int groupId);

    IPage<Competition> findAll(Page<Competition> page);

    IPage<Competition> find(IPage<Competition> page, String key);

    IPage<Competition> findPassByKey(IPage<Competition> page, String key);

    IPage<Competition> findPassAll(IPage<Competition> page);

    boolean setState(int id, String state);

    boolean setStartState(int id, String state);

    List<Competition> getEnterNoEnd();

    List<Competition> getStartNoEnd();

    List<Competition> get5ByType(int typeId);

    boolean setEnterState(int id, String state);

    String generateWord(int id);

    String generateWord(Competition competition);

    FileInputStream getWord(int competitionId);
}
