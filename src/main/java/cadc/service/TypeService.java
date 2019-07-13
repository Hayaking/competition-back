package cadc.service;

import cadc.entity.CompetitionType;

import java.util.List;

/**
 * @author haya
 */
public interface TypeService {
    /**
     * 查找竞赛级别
     * @return
     */
    List<CompetitionType> findCompetitionType();
}
