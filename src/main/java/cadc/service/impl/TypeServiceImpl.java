package cadc.service.impl;

import cadc.entity.CompetitionType;
import cadc.entity.JoinType;
import cadc.mapper.CompetitionTypeMapper;
import cadc.service.TypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl implements TypeService {
    @Resource
    private CompetitionTypeMapper competitionTypeMapper;

    @Override
    public List<CompetitionType> findCompetitionType() {
        QueryWrapper<CompetitionType> wrapper = new QueryWrapper<>();
        return competitionTypeMapper.selectList(wrapper);
    }

    @Override
    public List<JoinType> findJoinType() {
        return new JoinType().selectList( new QueryWrapper<>() );
    }
}
