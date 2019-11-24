package cadc.service;

import cadc.bean.holder.EnterHolder;
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

    IPage<Join> getByStudentId(Page<Join> page, int id);

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

    /**
     * 根据 competitionId progressId获取参赛学生list
     * @param page
     * @param competitionId
     * @param progressId
     * @return
     */
    IPage<Join> getEnterList(Page<Join> page, int competitionId, int progressId);

    Boolean setApplyState(Boolean flag, int joinId, int teacherId);

    /**
     * 生成参赛excel
     * @param competitionId
     * @return
     */
    String generateEnterListExcel(int competitionId, int progressId);

    boolean setEnterState(Boolean flag, int joinId);

    List<Join> getByGroupId(int groupId);

    Page<Join> getListByProgressId(Page<Join> page, int progressId);

    /**
     * 学生创建参赛
     * @param student
     * @param enterHolder
     * @return
     */
    boolean createJoin(Student student, EnterHolder enterHolder);

    boolean deleteById(int id);
}
