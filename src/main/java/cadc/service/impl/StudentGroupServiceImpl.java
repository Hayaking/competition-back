package cadc.service.impl;

import cadc.entity.StudentGroup;
import cadc.entity.StudentInGroup;
import cadc.mapper.StudentGroupMapper;
import cadc.mapper.StudentInGroupMapper;
import cadc.service.StudentGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.geom.QuadCurve2D;
import java.util.List;

/**
 * @author haya
 */
@Service
public class StudentGroupServiceImpl extends ServiceImpl<StudentGroupMapper, StudentGroup> implements StudentGroupService {
    @Resource
    private StudentGroupMapper studentGroupMapper;
    @Resource
    private StudentInGroupMapper studentInGroupMapper;

    @Override
    public IPage<StudentGroup> getByStudentId(Page<StudentGroup> page, int id) {
        return page.setRecords( studentGroupMapper.getWithWorksByStudentId( page, id ) );
    }
}
