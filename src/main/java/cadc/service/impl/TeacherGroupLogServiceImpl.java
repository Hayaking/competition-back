package cadc.service.impl;

import cadc.entity.TeacherGroupLog;
import cadc.mapper.TeacherGroupLogMapper;
import cadc.service.TeacherGroupLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
public class TeacherGroupLogServiceImpl extends ServiceImpl<TeacherGroupLogMapper, TeacherGroupLog> implements TeacherGroupLogService {
    @Resource
    private TeacherGroupLogMapper teacherGroupLogMapper;

    @Async
    @Override
    public boolean add(TeacherGroupLog teacherGroupLog) {
        return teacherGroupLog.insert();
    }

    @Override
    public IPage<TeacherGroupLog> getList(Page<TeacherGroupLog> page, Integer groupId) {
        QueryWrapper<TeacherGroupLog> wrapper = new QueryWrapper<>();
        wrapper.eq( "group_id", groupId ).orderByDesc( "create_time" );
        return teacherGroupLogMapper.selectPage( page,wrapper );
    }
}
