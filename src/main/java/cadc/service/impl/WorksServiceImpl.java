package cadc.service.impl;

import cadc.entity.Works;
import cadc.mapper.WorksMapper;
import cadc.service.WorksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author haya
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {

    @Resource
    private WorksMapper worksMapper;
}
