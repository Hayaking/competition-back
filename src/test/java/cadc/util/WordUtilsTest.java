package cadc.util;

import cadc.bean.holder.Cost;
import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CompetitionApplyPolicy;
import cadc.bean.word.CostPolicy;
import cadc.bean.word.ProcessPolicy;
import cadc.entity.Competition;
import cadc.entity.Process;
import cadc.entity.Progress;
import cadc.mapper.CompetitionMapper;
import cadc.service.ProgressService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class WordUtilsTest {
    @Resource
    private CompetitionMapper competitionMapper;
    @Autowired
    private ProgressService progressService;

    @Test
    public void poi3() throws IOException {
    }

    @Test
    public void poi4() throws IOException {
        Progress progress = progressService.getNeedReviewById( 18 );

        Map<String, Object> props = new HashMap<String, Object>() {{
            put( "name", progress.getCompetition().getName() );
            put( "personInCharge", progress.getCompetition().getPersonInCharge().getTeacherName() );
            put( "phone", progress.getCompetition().getPersonInCharge().getTeacherPhone() );
            put( "startDate", progress.getStartTime() );
            put( "endDate", progress.getEndTime() );

            put( "summary", "111");
            put( "costList", new LinkedList<Cost>(  ){{
                add( new Cost(){{ setValue( "1" );setReason( "1" );setSubject( "1" );}} );
                add( new Cost(){{ setValue( "1" );setReason( "1" );setSubject( "1" );}} );
                add( new Cost(){{ setValue( "1" );setReason( "1" );setSubject( "1" );}} );
                add( new Cost(){{ setValue( "1" );setReason( "1" );setSubject( "1" );}} );
            }} );
            put( "processList", new LinkedList<Process>(  ){{
                add( new Process(){{ setDescription( "1112" );}} );
                add( new Process(){{ setDescription( "1112" );}} );
                add( new Process(){{ setDescription( "1112" );}} );
            }} );
        }};
        XWPFTemplate
                .compile( "C:\\Users\\haya\\Desktop\\result.docx", Configure
                        .newBuilder()
                        .customPolicy( "costList", new CostPolicy(((List<Process>)props.get( "processList" )).size() -1) )
                        .customPolicy( "processList", new ProcessPolicy() )
                        .build() )
                .render( props )
                .writeToFile( "C:\\Users\\haya\\Desktop\\out_template.docx" );
    }
}
