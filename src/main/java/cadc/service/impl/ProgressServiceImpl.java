package cadc.service.impl;

import cadc.bean.PROGRESS_STATE;
import cadc.bean.holder.ResultSummaryHolder;
import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CostPolicy;
import cadc.bean.word.ProcessPolicy;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.mapper.ProgressMapper;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
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
    public boolean generateResultWord(Integer progressId, ResultSummaryHolder holder) {
        Progress progress = progressMapper.getNeedReviewById( progressId );
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String outPath = root + "static/result/" + progressId + ".docx";
        File outFile = new File( outPath );
        try {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Map<String, Object> props = new HashMap<String, Object>() {{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
                put( "name", progress.getCompetition().getName() );
                put( "personInCharge", progress.getCompetition().getPersonInCharge().getTeacherName() );
                put( "phone", progress.getCompetition().getPersonInCharge().getTeacherPhone() );
                put( "startDate", sdf.format( progress.getStartTime() ) );
                put( "endDate", sdf.format( progress.getEndTime() ) );

                put( "summary", holder.getSummary() );
                put( "costList", holder.getCostList() );
                put( "processList", holder.getProcessList() );
            }};
            XWPFTemplate
                    .compile( root + "template/result.docx", Configure
                            .newBuilder()
                            .customPolicy( "costList", new CostPolicy(((List<Process>)props.get( "processList" )).size() -1) )
                            .customPolicy( "processList", new ProcessPolicy() )
                            .build() )
                    .render( props )
                    .writeToFile( outPath );
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
