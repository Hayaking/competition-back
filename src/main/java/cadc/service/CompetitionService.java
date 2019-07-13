package cadc.service;

import cadc.entity.Competition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author haya
 */
public interface CompetitionService {
    /**
     * 增加一个竞赛
     * @param competition
     * @return
     */
    boolean insertCompetition(Competition competition);

    IPage<Competition> findByGroupId(Page<Competition> page, int groupId);
}
