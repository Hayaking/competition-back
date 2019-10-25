package cadc.service.impl;

import cadc.entity.Progress;
import cadc.mapper.ProgressMapper;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements ProgressService {
}
