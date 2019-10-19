package cadc.service;

import cadc.entity.Menu1;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yyp
 * @since 2019-03-26
 */
@Service
public interface Menu1Service extends IService<Menu1> {
    Menu1 getById(int id);

    List<Menu1> getMenuByUser(Object object);

    List<Menu1> getAll();

    List<Menu1> getByRoleId(int roleId);

    boolean setRoleAndMenu(int roleId, int menu1Id, int menu2Id, boolean flag);

    boolean setRoleAndMenu(int roleId, int menu1Id, boolean flag);
}
