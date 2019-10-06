package cadc.service;

import cadc.entity.Join;
import cadc.entity.Student;
import cadc.entity.StudentGroup;
import cadc.entity.Works;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author haya
 */
public interface JoinService extends IService<Join> {
    boolean create(Student student,StudentGroup group, List<String> list, Works works, Join join);

    IPage<Join> getByStudentAccount(Page<Join> page, String account);

    IPage<Join> getByLead(Page<Join> page, int teacherId);

    IPage<Join> getByCompetitionId(Page<Join> page, int competitionId);

    Boolean setApplyState(Boolean flag, int joinId, int teacherId);
}
