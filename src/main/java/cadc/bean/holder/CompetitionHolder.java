package cadc.bean.holder;

import cadc.entity.Budget;
import cadc.entity.Competition;
import cadc.entity.Progress;
import lombok.Data;

import java.util.List;

/**
 * @author haya
 */
@Data
public class CompetitionHolder {
    private Competition competition;
    private List<Budget> budgets;
    private List<Progress> progresses;
}
