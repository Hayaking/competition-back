package cadc.service.impl;

import cadc.entity.StudentInGroup;
import cadc.mapper.StudentInGroupMapper;
import cadc.service.StudentInGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cadc.bean.message.STATE.STATE_INVITING;

/**
 * @author haya
 */
@Service
public class StudentInGroupServiceImpl extends ServiceImpl<StudentInGroupMapper, StudentInGroup> implements StudentInGroupService {
    @Resource
    private StudentInGroupMapper studentInGroupMapper;
    @Override
    public boolean addList(List<String> list, int groupId) {
//        for (String str:list) {
//            studentInGroupMapper.insert( new StudentInGroup( str, groupId, STATE_INVITING.toString() ) );
//        }
        return true;
    }
}
