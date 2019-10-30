package cadc.mapper;

import cadc.entity.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
public interface ProcessMapper extends BaseMapper<Process> {
    @Select("select * from process where competition_id= #{competitionId}")
    List<Process> getByCompetitionId(Page<Process> page, @Param("competitionId") int competitionId);
}
