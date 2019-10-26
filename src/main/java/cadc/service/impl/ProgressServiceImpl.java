package cadc.service.impl;

import cadc.bean.message.STATE;
import cadc.entity.Progress;
import cadc.mapper.ProgressMapper;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cadc.bean.message.STATE.STATE_HAD_START;
import static cadc.bean.message.STATE.STATE_NOT_START;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements ProgressService {
    @Resource
    private ProgressMapper progressMapper;

    @Override
    public List<Progress> getEnterNoEnd() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "enter_state", STATE_NOT_START.toString() ).or()
                .eq( "enter_state", STATE_HAD_START.toString() );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public List<Progress> getStartNoEnd() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "start_state", STATE_NOT_START.toString() ).or()
                .eq( "start_state", STATE_HAD_START.toString() );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public boolean setEnterState(int id, STATE state) {
        Progress progress = progressMapper.selectById( id );
        UpdateWrapper<Progress> wrapper = new UpdateWrapper<>();
        wrapper.set( "enter_state", state.toString() );
        return progressMapper.update( progress, wrapper ) > 0;
    }

    @Override
    public boolean setStartState(int id, STATE state) {
        Progress progress = progressMapper.selectById( id );
        UpdateWrapper<Progress> wrapper = new UpdateWrapper<>();
        wrapper.set( "start_state", state.toString() );
        return progressMapper.update( progress, wrapper ) > 0;
    }
}
