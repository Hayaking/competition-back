package cadc.service.impl;

import cadc.bean.excel.EnterExcel;
import cadc.bean.excel.EnterExcel2;
import cadc.bean.excel.ExcelModel;
import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CompetitionApplyPolicy;
import cadc.entity.Competition;
import cadc.entity.Join;
import cadc.entity.Progress;
import cadc.mapper.CompetitionMapper;
import cadc.mapper.JoinMapper;
import cadc.mapper.ProgressMapper;
import cadc.service.DownLoadService;
import cadc.util.ExcelUtils;
import cadc.util.WordUtils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
@Service
public class DownLoadServiceImpl implements DownLoadService {

    @Resource
    private CompetitionMapper competitionMapper;
    @Resource
    private ProgressMapper progressMapper;
    @Resource
    private JoinMapper joinMapper;

    @Override
    public FileInputStream getCompetitionApplyWord(int competitionId) {
        Competition competition = competitionMapper.getWithProgressListById( competitionId );
        Map<String, Object> props = WordUtils.competitionMapToWord( competition );
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String outPath = root + "static/competition/" + competitionId + ".docx";
        File outFile = new File( outPath );
        FileInputStream fis;
        try {
            // 已存在docx
//            if (outFile.exists()) {
//                fis = new FileInputStream( outFile );
//                return fis;
//            }
            // 父文件夹不存在
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            XWPFTemplate
                    .compile( root + "template/competition.docx", Configure
                            .newBuilder()
                            .customPolicy( "progress", new CompetitionApplyPolicy() )
                            .build() )
                    .render( props )
                    .writeToFile( outPath );
            fis = new FileInputStream( new File( outPath ) );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fis;
    }

    @Override
    public FileInputStream getBudgetApplyWord(int competitionId) {
        Competition competition = competitionMapper.getWithBudgetListById( competitionId );
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String outPath = root + "static/budget/" + competitionId + ".docx";
        File outFile = new File( outPath );
        FileInputStream fis;
        try {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Map<String, Object> props = new HashMap<String, Object>( 2 ) {{
                put( "name", competition.getName() );
                put( "budget", competition.getProgressList() );
            }};
            XWPFTemplate
                    .compile( root + "template/budget.docx", Configure
                            .newBuilder()
                            .customPolicy( "budget", new BudgetPolicy() )
                            .build() )
                    .render( props )
                    .writeToFile( outPath );
            fis = new FileInputStream( new File( outPath ) );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fis;
    }

    @Override
    public FileInputStream getProgressResult(int progressId) {
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String outPath = root + "static/result/" + progressId + ".docx";
        FileInputStream fis;
        try {
            fis = new FileInputStream( new File( outPath ) );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return fis;
    }

    @Override
    public FileInputStream getEnterListExcel(int competitionId, int progressId) {
//        Competition competition = competitionMapper.getById( competitionId );
        // 参赛类型
        Progress progress = progressMapper.getById( progressId );
        Boolean isNeedWorks = progress.getIsNeedWorks();
        Boolean isSingle = progress.getIsSingle();
        List<Join> joinList = joinMapper.getListByCompetitionIdProgressId( competitionId, progressId );
        List<ExcelModel> data = new LinkedList<>();
        if (!isSingle) {
            //小组赛
            joinList.spliterator().forEachRemaining( item -> {
                StringBuilder members = new StringBuilder();
                item.getWorks()
                        .getStudentGroup()
                        .getMembers()
                        .spliterator()
                        .forEachRemaining( member ->
                                members.append( member.getStudent().getStuName() + "," )
                        );
                data.add( new EnterExcel(
                        item.getId(),
                        item.getWorks().getWorksName(),
                        members.toString(),
                        "" )
                );
            } );
        } else {
            //单人赛
            joinList.spliterator().forEachRemaining( item -> {
                data.add( isNeedWorks
                        ? EnterExcel.builder()
                        .index( item.getId() )
                        .worksName( item.getWorks().getWorksName() )
                        .member( item.getCreator().getStuName() )
                        .lead1( "" ).build()
                        : EnterExcel2.builder()
                        .index( item.getId() )
                        .member( item.getCreator().getStuName() )
                        .lead1( "" ).build() );
            } );
        }
        String fileName = LocalDate.now().toString() + competitionId;
        return ExcelUtils.generateExcel( fileName, "", data, isNeedWorks ? EnterExcel.class : EnterExcel2.class );
    }
}
