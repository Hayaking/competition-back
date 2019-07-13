package cadc.service.impl;

import cadc.entity.TeacherGroup;
import cadc.mapper.TeacherGroupMapper;
import cadc.service.TeacherGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haya
 */
@Service
public class TeacherGroupServiceImpl extends ServiceImpl<TeacherGroupMapper, TeacherGroup> implements TeacherGroupService {
    @Resource
    private TeacherGroupMapper teacherGroupMapper;

    @Override
    public List<TeacherGroup> findByTeacherId(String account) {
        return teacherGroupMapper.findByTeacherId( account );
    }
}
