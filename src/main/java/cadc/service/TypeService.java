package cadc.service;

import cadc.entity.CompetitionType;
import cadc.entity.JoinType;
import cadc.entity.PriceType;

import java.util.List;

/**
 * @author haya
 */
public interface TypeService {
    /**
     * 查找竞赛级别
     *
     * @return
     */
    List<CompetitionType> findCompetitionType();

    List<JoinType> findJoinType();

    List<PriceType> findPriceType();
}
