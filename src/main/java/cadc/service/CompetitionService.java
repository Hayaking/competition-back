package cadc.service;

import cadc.entity.Competition;

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
}
