package cadc.service.impl;

import cadc.entity.Join;
import cadc.mapper.JoinMapper;
import cadc.service.JoinService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public class JoinServiceImpl extends ServiceImpl<JoinMapper, Join> implements JoinService {
    @Resource
    private JoinMapper joinMapper;

    @Override
    public IPage<Join> getByStudentAccount(Page<Join> page,String account) {
        List<Join> list = joinMapper.getListByStudentAccount( account );
        page.setRecords( list );
        return page;
    }
}
