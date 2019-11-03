package cadc.service.impl;

import cadc.entity.Works;
import cadc.mapper.WorksMapper;
import cadc.service.WorksService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {

    @Resource
    private WorksMapper worksMapper;

    @Override
    public List<Works> getByGroupId(int groupId) {
        return worksMapper.getSimpleListByGroupId( groupId );
    }
}
