package cadc.service.impl;

import cadc.entity.*;
import cadc.mapper.*;
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
    @Resource
    private JoinMapper joinMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentInGroupMapper studentInGroupMapper;

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

    @Override
    public boolean reviewResult(JoinInProgress jip, Integer reviewState, Boolean editState) {
        // 1.设置状态
        jip.setReviewState( reviewState );
        jip.setIsEditable( editState );
        //得奖了
        if (reviewState == 1) {
            // 2.获得比赛阶段
            int progressId = jip.getProgressId();
            Progress progress = progressMapper.selectById( progressId );
            Boolean isSingle = progress.getIsSingle();
            // 2.获得参赛人员
            int joinId = jip.getJoinId();
            Join join = joinMapper.selectById( joinId );
            //单人赛
            if (isSingle) {
                Integer creatorId = join.getCreatorId();
                Student student = studentMapper.selectById( creatorId );
                // 获奖数+1
                student.setPriceNum( student.getPriceNum() + 1 );
                student.insertOrUpdate();
            } else {
                Integer groupId = join.getGroupId();
                List<StudentInGroup> sigList = studentInGroupMapper.getStudentInGroupByGroupId( groupId );
                for (StudentInGroup item : sigList) {
                    Student student = item.getStudent();
                    student.setPriceNum( student.getPriceNum() + 1 );
                    student.insertOrUpdate();
                }
            }
        }
        return true;
    }

}
