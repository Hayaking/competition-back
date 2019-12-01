package cadc.service;

import java.io.FileInputStream;

/**
 * @author haya
 */
public interface DownLoadService {
    FileInputStream getCompetitionApplyWord(int competitionId);

    FileInputStream getBudgetApplyWord(int competitionId);

    FileInputStream getProgressResult(int progressId);

    FileInputStream getEnterListExcel(int competitionId, int progressId);
}
