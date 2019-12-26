package cadc.service;

import cadc.bean.holder.ResultSummaryHolder;

import java.io.FileInputStream;

/**
 * @author haya
 */
public interface DownLoadService {
    FileInputStream getCompetitionApplyWord(int competitionId);

    FileInputStream getBudgetApplyWord(int competitionId);

    FileInputStream generateResultApplyWord(int progressId, ResultSummaryHolder holder);

    FileInputStream getResultApplyWord(int progressId);

    FileInputStream getEnterListExcel(int competitionId, int progressId);
}
