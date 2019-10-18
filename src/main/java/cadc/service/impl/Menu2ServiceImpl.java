package cadc.service.impl;

import cadc.entity.Menu2;
import cadc.mapper.Menu2Mapper;
import cadc.service.Menu2Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Menu2ServiceImpl extends ServiceImpl<Menu2Mapper, Menu2> implements Menu2Service {

    @Resource
    private Menu2Mapper menu2Mapper;

    @Override
    public List<Menu2> getListByFatherId(int fatherId) {
        QueryWrapper<Menu2> wrapper = new QueryWrapper<>();
        wrapper.eq( "father_id", fatherId );
        return menu2Mapper.selectList( wrapper );
    }

}
