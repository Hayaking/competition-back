package cadc.service.impl;

import cadc.entity.Process;
import cadc.mapper.ProcessMapper;
import cadc.service.ProcessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {
    @Resource
    private ProcessMapper processMapper;

    @Override
    public Page<Process> getByCompetitionId(Page<Process> page, int competitionId) {
        List<Process> list = processMapper.getByCompetitionId( page, competitionId );
        page.setRecords( list );
        return page;
    }

    @Override
    public List<Process> getListByProcessId(String progressId) {
        QueryWrapper<Process> wrapper = new QueryWrapper<>();
        wrapper.eq( "progress_id", progressId );
        return processMapper.selectList( wrapper );
    }
}
