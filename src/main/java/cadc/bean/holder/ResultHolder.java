package cadc.bean.holder;

import cadc.entity.Certificate;
import cadc.entity.Price;
import lombok.Data;

/**
 * @author haya
 */
@Data
public class ResultHolder {
    private Boolean isWinThePrice;
    private Price price;
    private Certificate certificate;
}
