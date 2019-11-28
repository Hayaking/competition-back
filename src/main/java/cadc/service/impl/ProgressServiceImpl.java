package cadc.service.impl;

import cadc.bean.PROGRESS_STATE;
import cadc.entity.Progress;
import cadc.mapper.ProgressMapper;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public List<Progress> getEnterNoStart() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "enter_state", STATE_NOT_START.toString() )
                .eq( "is_scan_enter_state", true );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public List<Progress> getEnterHadStart() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "enter_state", STATE_HAD_START.toString() )
                .eq( "is_scan_enter_state", true );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public List<Progress> getStartNoStart() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "start_state", STATE_NOT_START.toString() )
                .eq( "is_scan_start_state", true );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public List<Progress> getStartHadStart() {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "start_state", STATE_HAD_START.toString() )
                .eq( "is_scan_start_state", true );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public boolean setEnterState(int id, PROGRESS_STATE state) {
        Progress progress = progressMapper.selectById( id );
        progress.setEnterState( state.getCode() );
        return progress.insertOrUpdate();
    }

    @Override
    public boolean setStartState(int id, PROGRESS_STATE state) {
        Progress progress = progressMapper.selectById( id );
        progress.setStartState( state.getCode() );
        return progress.insertOrUpdate();
    }

    @Override
    public List<Progress> getByCompetitionId(int competitionId) {
        QueryWrapper<Progress> wrapper = new QueryWrapper<>();
        wrapper.eq( "competition_id", competitionId );
        return progressMapper.selectList( wrapper );
    }

    @Override
    public List<Progress> getListByJoinId(int joinId) {
        return progressMapper.getListByJoinId( joinId );
    }

    @Override
    public IPage<Progress> getNeedReviewList(Page<Progress> page) {
        List<Progress> list = progressMapper.getNeedReviewList( page );
        page.setRecords( list );
        return page;
    }
}
