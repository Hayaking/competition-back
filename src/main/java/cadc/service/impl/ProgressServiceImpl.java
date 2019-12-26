package cadc.service.impl;

import cadc.bean.PROGRESS_STATE;
import cadc.bean.holder.ResultSummaryHolder;
import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CostPolicy;
import cadc.bean.word.ProcessPolicy;
import cadc.entity.*;
import cadc.entity.Process;
import cadc.mapper.JoinInProgressMapper;
import cadc.mapper.ProgressMapper;
import cadc.mapper.WorkLoadMapper;
import cadc.service.ProgressService;
import cadc.util.WordUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private JoinInProgressMapper joinInProgressMapper;
    @Resource
    private WorkLoadMapper workLoadMapper;
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

    @Override
    public Progress getNeedReviewById(int id) {
        return progressMapper.getNeedReviewById( id );
    }



    @Override
    public boolean endProgress(int progressId) {
        double H = 0,F;
        //1.获取当前阶段
        Progress progress = progressMapper.getById( progressId );
        // 获取竞赛级别
        int typeId = progress.getTypeId();
        if (typeId == 3 || typeId == 4) {
            H = 1D;
        } else if (typeId == 2) {
            H = 0.8D;
        }
        // 获取参赛形式
        Boolean isSingle = progress.getIsSingle();
        Boolean isNeedWorks = progress.getIsNeedWorks();
        if (isSingle && !isNeedWorks) {
            F = 0.3D;
        } else {
            F = 1;
        }
        //2.获取报名列表
        List<JoinInProgress> enterList = joinInProgressMapper.getEnterList( progressId );

        for (JoinInProgress item : enterList) {
            double L;
            //获取获奖级别
            int priceType = item.getPrice().getTypeId();
            if (priceType <= 3) {
                L = 1D;
            } else {
                L = 0.6D;
            }
            Teacher teacher = item.getJoin().getTeacher1();
            WorkLoad workLoad = new WorkLoad();
            workLoad.setJoinId( item.getJoinId() );
            workLoad.setTeacherId( teacher.getId() );
            //计算工作量
            workLoad.setVal( 20 * H + F + L );
            workLoad.insert();
        }
        return true;
    }
}
