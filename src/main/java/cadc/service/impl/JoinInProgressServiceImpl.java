package cadc.service.impl;

import cadc.entity.JoinInProgress;
import cadc.mapper.JoinInProgressMapper;
import cadc.service.JoinInProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author haya
 */
@Service
public class JoinInProgressServiceImpl extends ServiceImpl<JoinInProgressMapper, JoinInProgress> implements JoinInProgressService {
}
