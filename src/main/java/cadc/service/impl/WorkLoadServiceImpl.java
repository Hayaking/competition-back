package cadc.service.impl;

import cadc.entity.WorkLoad;
import cadc.mapper.WorkLoadMapper;
import cadc.service.WorkLoadService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
public class WorkLoadServiceImpl extends ServiceImpl<WorkLoadMapper, WorkLoad> implements WorkLoadService {

    @Resource
    private WorkLoadMapper workLoadMapper;

    @Override
    public IPage<WorkLoad> getWorkLoad(Page<WorkLoad> page, int id) {
        List<WorkLoad> list = workLoadMapper.getWorkLoadPage( page, id );
        page.setRecords( list );
        return page;
    }
}
