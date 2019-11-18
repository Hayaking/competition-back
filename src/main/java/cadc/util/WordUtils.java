package cadc.util;

import cadc.entity.Budget;
import cadc.entity.Competition;
import cadc.entity.Progress;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    public static FileInputStream generateWord(String root, String fileName, Map<String, Object> map) throws IOException, TemplateException {
        File file = new File( root + "word/" + fileName );
        if (!file.exists()) {
            Configuration configuration = new Configuration( new Version( "2.3.0" ) ) {{
                setDefaultEncoding( "utf-8" );
                setDirectoryForTemplateLoading( new File( root + "template/" ) );
            }};
            Template template = configuration.getTemplate( "word.ftl" );
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file ), StandardCharsets.UTF_8 ), 10240 );
            template.process( map, writer );
            writer.close();
        }
        return new FileInputStream( file );
    }

    public static FileInputStream generateBugetWord(String root, String fileName, Map<String, Object> map) throws IOException, TemplateException {
        File file = new File( root + "word/" + fileName );
        if (!file.exists()) {
            Configuration configuration = new Configuration( new Version( "2.3.0" ) ) {{
                setDefaultEncoding( "utf-8" );
                setDirectoryForTemplateLoading( new File( root + "template/" ) );
            }};
            Template template = configuration.getTemplate( "budget.ftl" );
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file ), StandardCharsets.UTF_8 ), 10240 );
            template.process( map, writer );
            writer.close();
        }
        return new FileInputStream( file );
    }

    public static Map<String, Object> competitionMapToWord(Competition competition) {
        HashMap<String, Object> map = new HashMap<>();
        map.put( "name", competition.getName() );
        map.put( "personIncharge", competition.getPersonInCharge().getTeacherName() );
        map.put( "groupNum", competition.getExGroupNum() );
        map.put( "stuNum", String.valueOf( competition.getExStuNum() ) );
        map.put( "exRes", competition.getExRes() );
        map.put( "intro", competition.getIntro() );
        map.put( "process", competition.getProcess() );
        map.put( "createDate", new SimpleDateFormat( "yyyy年MM月dd日" ).format( competition.getCreateTime() ) );
        List<Progress> progressList = competition.getProgressList();
        Progress highestLevel = progressList.get( progressList.size() - 1 );
        map.put( "highestLevel", String.valueOf( highestLevel.getType().getTypeName() ) );
        for (int i = 1; i <= 4; i++) {
            Progress progress = null;
            if (progressList.size() >= i) {
                progress = progressList.get( i - 1 );
                map.put( "p" + i + "Name", progress.getName() );
                map.put( "p" + i + "Type", progress.getType().getTypeName() );
                map.put( "p" + i + "Date", new SimpleDateFormat( "yyyy年MM月dd日" ).format( progress.getStartTime() ) );
                map.put( "orgAndCoorg" + i, "" );
            } else {
                map.put( "p" + i + "Name", "" );
                map.put( "p" + i + "Type", "" );
                map.put( "p" + i + "Date", "" );
                map.put( "orgAndCoorg" + i, "" );
            }
        }
        return map;
    }

    public static Map<String, Object> budgetMapToWord(Competition competition) {
        HashMap<String, Object> map = new HashMap<>();
        map.put( "competitionName", competition.getName() );
        List<Progress> progressList = competition.getProgressList();
        List<Budget> budgetList = progressList.stream().map( Progress::getBudget ).collect( Collectors.toList() );
        double cEnter = 0, cTravel = 0, cThing = 0, cOther = 0, cSum = 0;
        for (int i = 1; i <= 3; i++) {
            if (i > progressList.size()) {
                map.put( "p" + i + "Name", "" );
                map.put( "p" + i + "Type", "" );
                map.put( "p" + i + "Enter", "" );
                map.put( "p" + i + "Travel", "" );
                map.put( "p" + i + "Thing", "" );
                map.put( "p" + i + "Other", "" );
                map.put( "p" + i + "Reason", "" );
                map.put( "p" + i + "RowSum", "" );
            }
            else {
                Budget budget = budgetList.get( i -1);
                Progress progress = progressList.get( i-1 );
                map.put( "p" + i + "Name", progress.getName() );
                map.put( "p" + i + "Type", progress.getType().getTypeName() );
                map.put( "p" + i + "Enter", budget.getEnter() );
                map.put( "p" + i + "Travel", budget.getTravel() );
                map.put( "p" + i + "Thing", budget.getThing() );
                map.put( "p" + i + "Other", budget.getOther() );
                map.put( "p" + i + "Reason", budget.getReason() );
                double sum = budget.getEnter() + budget.getTravel() + budget.getThing() + budget.getOther();
                map.put( "p" + i + "RowSum", sum );
                cEnter += budget.getEnter();
                cThing += budget.getThing();
                cTravel += budget.getTravel();
                cOther += budget.getOther();
                cSum += sum;
            }
        }
        map.put( "cEnter", cEnter );
        map.put( "cThing", cThing );
        map.put( "cTravel", cTravel );
        map.put( "cOther", cOther );
        map.put( "cSum", cSum );
        return map;
    }
}
