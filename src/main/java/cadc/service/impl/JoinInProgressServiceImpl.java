package cadc.service.impl;

import cadc.entity.JoinInProgress;
import cadc.entity.Progress;
import cadc.mapper.JoinInProgressMapper;
import cadc.mapper.ProgressMapper;
import cadc.service.JoinInProgressService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        joinInProgress.setIsPromotion( flag );
        if (flag) {
            int joinId = joinInProgress.getJoinId();
            int progressId = joinInProgress.getProgressId();
            int competitionId = progressMapper.selectById( progressId ).getCompetitionId();
            // 获取竞赛所有阶段
            List<Progress> progressList = progressMapper.getListByCompetitionId( competitionId );
            // 获取所有阶段的Id
            List<Integer> idList = progressList.stream().map( Progress::getId ).collect( Collectors.toList() );
            // 找到当前阶段索引
            int index = idList.indexOf( progressId );
            if (index == idList.size() - 1) {

            } else {
                // 下一阶段Id
                Integer nextId = idList.get( index + 1 );
                JoinInProgress next = new JoinInProgress() {{
                    setProgressId( nextId );
                    setJoinId( joinId );
                }};
                next.insert();
            }
        }
        return joinInProgress.insertOrUpdate();
    }

    @Override
    public List<JoinInProgress> getListByJoinId(int joinId) {
        List<JoinInProgress> list = joinInProgressMapper.getListByJoinId( joinId );
        return list;
    }

    @Override
    public Page<JoinInProgress> getEnterPage(Page<JoinInProgress> page, int competitionId, int progressId) {
        List<JoinInProgress> list = joinInProgressMapper.getEnterList( page, progressId );
        page.setRecords( list );
        return page;
    }

    @Override
    public boolean setEnterState(int inProgressId, Integer flag) {
        JoinInProgress jip = joinInProgressMapper.selectById( inProgressId );
        jip.setEnterState( flag );
//        jip.setIsEnter( flag );
        return jip.insertOrUpdate();
    }

    @Override
    public Page<JoinInProgress> getResultListByProgressId(Page<JoinInProgress> page, int progressId) {
        List<JoinInProgress> list = joinInProgressMapper.getResultListByProgressId( page, progressId );
        page.setRecords( list );
        return page;
    }
}
