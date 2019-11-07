package cadc.service.impl;

import cadc.bean.JOIN_STATE;
import cadc.entity.JoinInProgress;
import cadc.entity.Progress;
import cadc.mapper.JoinInProgressMapper;
import cadc.mapper.ProgressMapper;
import cadc.service.JoinInProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author haya
 */
@Service
public class JoinInProgressServiceImpl extends ServiceImpl<JoinInProgressMapper, JoinInProgress> implements JoinInProgressService {
    @Resource
    private JoinInProgressMapper joinInProgressMapper;
    @Resource
    private ProgressMapper progressMapper;
    @Override
    public Boolean promotion(Integer joinInProgressId, Boolean flag) {
        JoinInProgress joinInProgress = joinInProgressMapper.getById( joinInProgressId );
        if (JOIN_STATE.PROMOTION.toString().equals( joinInProgress.getState() )) {
            return true;
        }
        if (flag) {
            int joinId = joinInProgress.getJoinId();
            int progressId = joinInProgress.getProgressId();
            int competitionId = progressMapper.selectById( progressId ).getCompetitionId();

            List<Progress> progressList = progressMapper.getListByCompetitionId( competitionId );
            List<Integer> idList = progressList.stream().map( Progress::getId ).collect( Collectors.toList() );
            int index = idList.indexOf( progressId );
            if (index == idList.size() - 1) {

            } else {
                Integer nextId = idList.get( index + 1 );
                JoinInProgress next = new JoinInProgress() {{
                    setProgressId( nextId );
                    setJoinId( joinId );
                    setState( JOIN_STATE.NO_START.toString() );
                }};
                next.insert();
            }
        }
        joinInProgress.setState( flag ? JOIN_STATE.PROMOTION.toString() : JOIN_STATE.NO_PROMOTION.toString() );
        return joinInProgress.insertOrUpdate();
    }
}
