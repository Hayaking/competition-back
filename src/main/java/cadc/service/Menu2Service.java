package cadc.service;

import cadc.entity.Menu2;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
public interface Menu2Service extends IService<Menu2> {
    List<Menu2> getListByFatherId(int fatherId);
}
