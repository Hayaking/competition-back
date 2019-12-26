package cadc.util;

import cadc.bean.holder.ResultSummaryHolder;
import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CompetitionApplyPolicy;
import cadc.bean.word.CostPolicy;
import cadc.bean.word.ProcessPolicy;
import cadc.entity.Budget;
import cadc.entity.Competition;
import cadc.entity.Process;
import cadc.entity.Progress;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import freemarker.template.TemplateException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haya
 */
public class WordUtils {

    /**
     * 生成竞赛申请表
     *
     * @param root
     * @param fileName
     * @param map
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static FileInputStream generateCompetitionWord(String root, String fileName, Map<String, Object> map) throws IOException, TemplateException {
        String outPath = root + "static/competition/" + fileName + ".docx";
        XWPFTemplate
                .compile(root + "template/competition.docx", Configure
                        .newBuilder()
                        .customPolicy("progress", new CompetitionApplyPolicy())
                        .build())
                .render(map)
                .writeToFile(outPath);
        return new FileInputStream(new File(outPath));
    }

    /**
     * 生成竞赛经费预算表
     *
     * @param root
     * @param fileName
     * @param map
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static FileInputStream generateBugetWord(String root, String fileName, Map<String, Object> map) throws IOException, TemplateException {
        String outPath = root + "static/budget/" + fileName + ".docx";
        XWPFTemplate
                .compile(root + "template/budget.docx", Configure
                        .newBuilder()
                        .customPolicy("budget", new BudgetPolicy())
                        .build())
                .render(map)
                .writeToFile(outPath);
        return new FileInputStream(new File(outPath));
    }

    /**
     * 生成竞赛结果总结表
     *
     * @param root
     * @param fileName
     * @param map
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static FileInputStream generateResultWord(String root, String fileName, Map<String, Object> map) throws IOException, TemplateException {
        String outPath = root + "static/result/" + fileName + ".docx";
        XWPFTemplate
                .compile(root + "template/result.docx", Configure
                        .newBuilder()
                        .customPolicy("costList", new CostPolicy(((List<Process>) map.get("processList")).size() - 1))
                        .customPolicy("processList", new ProcessPolicy())
                        .build())
                .render(map)
                .writeToFile(outPath);
        return new FileInputStream(outPath);
    }

    public static Map<String, Object> competitionMapToWord(Competition competition) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", competition.getName());
        map.put("personIncharge", competition.getPersonInCharge().getTeacherName());
        map.put("phone", competition.getPersonInCharge().getTeacherPhone());
        map.put("groupNum", String.valueOf(competition.getExGroupNum()));
        map.put("stuNum", String.valueOf(competition.getExStuNum()));
        map.put("exRes", competition.getExRes());
        map.put("intro", competition.getIntro());
        map.put("process", competition.getProcess());
        map.put("exCondition", competition.getExCondition());
        map.put("createDate", new SimpleDateFormat("yyyy年MM月dd日").format(competition.getCreateTime()));
        List<Progress> progressList = competition.getProgressList();
        map.put("progress", progressList);
        Progress highestLevel = progressList.get(progressList.size() - 1);
        map.put("highestLevel", String.valueOf(highestLevel.getType().getTypeName()));
        return map;
    }

    public static Map<String, Object> budgetMapToWord(Competition competition) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", competition.getName());
        map.put("budget", competition.getProgressList());
        return map;
    }

    public static Map<String, Object> resultMapToWord(Progress progress, ResultSummaryHolder holder) {
        HashMap<String, Object> map = new HashMap<>();
        SimpleDateFormat        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("name", progress.getCompetition().getName());
        map.put("personInCharge", progress.getCompetition().getPersonInCharge().getTeacherName());
        map.put("phone", progress.getCompetition().getPersonInCharge().getTeacherPhone());
        map.put("startDate", sdf.format(progress.getStartTime()));
        map.put("endDate", sdf.format(progress.getEndTime()));
        map.put("summary", holder.getSummary());
        map.put("costList", holder.getCostList());
        map.put("processList", holder.getProcessList());
        return map;
    }
}