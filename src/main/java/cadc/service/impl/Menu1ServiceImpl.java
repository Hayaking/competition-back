package cadc.service.impl;

import cadc.entity.Menu1;
import cadc.mapper.Menu1Mapper;
import cadc.service.Menu1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Menu1ServiceImpl extends ServiceImpl<Menu1Mapper, Menu1> implements Menu1Service {
    @Resource
    private Menu1Mapper menu1Mapper;

    @Override
    public Menu1 getById(int id) {
        return menu1Mapper.getWithChildsById( id );
    }
}
