package cadc.service;

import cadc.entity.Certificate;
import cadc.entity.Price;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author haya
 */
public interface PriceService extends IService<Price> {
    boolean create(boolean isWinPrice, Price price, Certificate certificate);

    Price getByInProgressId(int joinInProgressId);
}
