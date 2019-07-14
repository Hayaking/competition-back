package cadc.service.impl;

import cadc.entity.Competition;
import cadc.mapper.CompetitionMapper;
import cadc.service.CompetitionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Override
    public IPage<Competition> findByGroupId(Page<Competition> page, int groupId) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "teacher_group_id", groupId );
        return competitionMapper.selectPage( page, wrapper );
    }

    @Override
    public IPage<Competition> findAll(Page<Competition> page) {
        return competitionMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public boolean setState(int id, String state) {
        QueryWrapper<Competition> wrapper = new QueryWrapper<>();
        wrapper.eq( "id", id );
        Competition competition = competitionMapper.selectOne( wrapper );
        competition.setState( state );
        return competitionMapper.updateById( competition ) > 0;
    }
}
