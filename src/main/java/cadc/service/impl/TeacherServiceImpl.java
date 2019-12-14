package cadc.service.impl;

import cadc.entity.Teacher;
import cadc.mapper.TeacherMapper;
import cadc.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public boolean sign(Teacher teacher) {
        // 拿注册时间当盐
        Date signTime = new Date();
        teacher.setSignTime( signTime );
        ByteSource salt = ByteSource.Util.bytes( Long.toString( signTime.getTime()  ) );
        SimpleHash result = new SimpleHash( "MD5", teacher.getPassword(), salt, 10 );
        teacher.setPassword( result.toHex() );
        return teacher.insert();
    }

    @Override
    public Teacher find(String account, String password) {
        return teacherMapper.find( account, password );
    }

    @Override
    public Teacher find(String account) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.eq( "account", account );
        return teacherMapper.selectOne( wrapper );
    }

    @Override
    public List<Teacher> getByGroupId(int groupId) {
        return teacherMapper.getByGroupId( groupId );
    }

    @Override
    public List<Teacher> getInvitingByGroupId(int groupId) {
        return teacherMapper.getInvitingByGroupId( groupId );
    }

    @Override
    public IPage<Teacher> findAll(IPage<Teacher> page) {
        return teacherMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public IPage<Teacher> find(IPage<Teacher> page, String key) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.like( "account", key ).or()
                .like( "teacher_name", key );
        return teacherMapper.selectPage( page, wrapper );
    }

    @Override
    public List<Teacher> getByRole(int roleId) {
        return teacherMapper.getByRoleId( roleId );
    }

    @Override
    public IPage<Teacher> getPageByRole(Page<Teacher> page, Integer id) {
        List<Teacher> list = teacherMapper.getListByRoleId( page, id );
        page.setRecords( list );
        return page;
    }

}
