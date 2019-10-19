package cadc.service;

import cadc.entity.Menu1;
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
public interface Menu1Service extends IService<Menu1> {
    Menu1 getById(int id);
    List<Menu1> getMenuByUser(Object object);
}
