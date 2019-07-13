package cadc.service.impl;

import cadc.entity.Competition;
import cadc.mapper.CompetitionMapper;
import cadc.service.CompetitionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author haya
 */
@Service
public class CompetitionImpl implements CompetitionService {
    @Resource
    private CompetitionMapper competitionMapper;

    @Override
    public boolean insertCompetition(Competition competition) {
        return competitionMapper.insert( competition ) > 0;
    }
}
