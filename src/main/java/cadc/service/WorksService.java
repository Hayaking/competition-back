package cadc.service;

import cadc.entity.Works;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface WorksService extends IService<Works> {
    List<Works> getByGroupId(int groupId);
}
