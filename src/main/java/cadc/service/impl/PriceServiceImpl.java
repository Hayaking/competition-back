package cadc.service.impl;

import cadc.bean.PRICE_STATE;
import cadc.entity.Certificate;
import cadc.entity.Join;
import cadc.entity.JoinInProgress;
import cadc.entity.Price;
import cadc.mapper.JoinInProgressMapper;
import cadc.mapper.JoinMapper;
import cadc.mapper.PriceMapper;
import cadc.service.JoinService;
import cadc.service.PriceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static cadc.bean.message.STATE.STATE_APPLYING;

/**
 * @author haya
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PriceServiceImpl extends ServiceImpl<PriceMapper, Price> implements PriceService {

    @Resource
    private PriceMapper priceMapper;
    @Resource
    private JoinInProgressMapper joinInProgressMapper;

    @Resource
    private JoinMapper joinMapper;
    @Override
    public boolean create(boolean isWinPrice, Price price, Certificate certificate) {
        JoinInProgress jip = joinInProgressMapper.getById( price.getJoinInProgressId());
        jip.setIsPrice( isWinPrice );
        // 得奖了
        if (isWinPrice) {
            price.setPriceState( STATE_APPLYING.toString() );
            price.setState( PRICE_STATE.EDIT.getCode() );
            price.setJoinInProgressId( price.getJoinInProgressId() );
            price.setJoinId( joinInProgressMapper.selectById( price.getJoinInProgressId() ).getJoinId() );
            price.insert();
            certificate.setPriceId( price.getId() );
            certificate.insert();
        }
        return jip.insertOrUpdate();
    }

    @Override
    public Price getByInProgressId(int joinInProgressId) {
        QueryWrapper<Price> wrapper = new QueryWrapper<>();
        wrapper.eq( "join_in_progress_id", joinInProgressId );
        return priceMapper.selectOne( wrapper );
    }

    @Override
    public IPage<Price> getStudentPricePage(Page<Price> page, int id) {
        List<Join> joinList = joinMapper.getListByStudentId( id );
//        QueryWrapper<Price> wrapper = new QueryWrapper<>();
//        for (Join item : joinList) {
//            wrapper.eq( "join_id", item.getId() ).or();
//        }
        List<Price> list = priceMapper.getPageByStudent( page, joinList );
        page.setRecords( list );
        return page;
//        return priceMapper.selectPage( page, wrapper );
    }

    @Override
    public List<Price> getRecentlyPrice() {
        List<Price> list = priceMapper.getRecentLyPrice();

        return list;
    }

}
