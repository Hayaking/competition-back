package cadc.bean.holder;

import cadc.entity.Process;
import lombok.Data;

import java.util.List;

/**
 * @author haya
 */
@Data
public class ResultSummaryHolder {
    private String summary;
    private List<Process> processList;
    private List<Cost> costList;
}
