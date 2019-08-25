package cadc.service;

import cadc.entity.StudentInGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface StudentInGroupService extends IService<StudentInGroup> {
    boolean addList(List<String> list, int groupId);
}
