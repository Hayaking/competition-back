package cadc.service;

import cadc.entity.Certificate;
import cadc.entity.Price;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface PriceService extends IService<Price> {
    boolean create(boolean isWinPrice, Price price, Certificate certificate);

    Price getByInProgressId(int joinInProgressId);

    IPage<Price> getStudentPricePage(Page<Price> page, int id);

    List<Price> getRecentlyPrice();
}
