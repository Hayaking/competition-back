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

    /**
     * 搜索指导申请
     * @param page
     * @param key
     * @param teacherId
     * @return
     */
    IPage<Join> searchByLead(Page<Join> page, String key, int teacherId);

    /**
     * 根据competitionId获取参赛列表
     * @param page
     * @param competitionId
     * @return
     */
    IPage<Join> getByCompetitionId(Page<Join> page, int competitionId);

    Boolean setApplyState(Boolean flag, int joinId, int teacherId);

    /**
     * 生成参赛excel
     * @param competitionId
     * @return
     */
    String generateEnterListExcel(int competitionId);

    boolean setEnterState(Boolean flag, int joinId);
}
