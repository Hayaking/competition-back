package cadc.util;

import cadc.bean.word.BudgetPolicy;
import cadc.bean.word.CompetitionApplyPolicy;
import cadc.entity.Competition;
import cadc.entity.Progress;
import cadc.mapper.CompetitionMapper;
import cadc.service.ProgressService;
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

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class WordUtilsTest {
    @Resource
    private CompetitionMapper competitionMapper;
    @Autowired
    private ProgressService progressService;

    @Test
    public void poi() throws IOException {

        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream( new File( "D:\\create_table.docx" ) );

        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment( ParagraphAlignment.CENTER );

        XWPFRun titleParagraphRun = titleParagraph.createRun();

        titleParagraphRun.setText( "Java PoI" );
        titleParagraphRun.setColor( "000000" );
        titleParagraphRun.setFontSize( 20 );

        //段落
        XWPFParagraph firstParagraph = document.createParagraph();
        XWPFRun run = firstParagraph.createRun();
        run.setText( "Java POI 生成word文件。" );
        run.setColor( "696969" );
        run.setFontSize( 16 );

        //设置段落背景颜色
        CTShd cTShd = run.getCTR().addNewRPr().addNewShd();
        cTShd.setVal( STShd.CLEAR );
        cTShd.setFill( "97FFFF" );

        //换行
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText( "\r" );

        //基本信息表格
        XWPFTable infoTable = document.createTable();
        //去表格边框
        infoTable.getCTTbl().getTblPr().unsetTblBorders();

        //列宽自动分割
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType( STTblWidth.DXA );
        infoTableWidth.setW( BigInteger.valueOf( 9072 ) );

        //表格第一行
        XWPFTableRow infoTableRowOne = infoTable.getRow( 0 );
        infoTableRowOne.getCell( 0 ).setText( "职位" );
        infoTableRowOne.addNewTableCell().setText( ": Java 开发工程师" );

        //表格第二行
        XWPFTableRow infoTableRowTwo = infoTable.createRow();
        infoTableRowTwo.getCell( 0 ).setText( "姓名" );
        infoTableRowTwo.getCell( 1 ).setText( ": seawater" );

        //表格第三行
        XWPFTableRow infoTableRowThree = infoTable.createRow();
        infoTableRowThree.getCell( 0 ).setText( "生日" );
        infoTableRowThree.getCell( 1 ).setText( ": xxx-xx-xx" );

        //表格第四行
        XWPFTableRow infoTableRowFour = infoTable.createRow();
        infoTableRowFour.getCell( 0 ).setText( "性别" );
        infoTableRowFour.getCell( 1 ).setText( ": 男" );

        //表格第五行
        XWPFTableRow infoTableRowFive = infoTable.createRow();
        infoTableRowFive.getCell( 0 ).setText( "现居地" );
        infoTableRowFive.getCell( 1 ).setText( ": xx" );
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy( document, sectPr );

        //添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        String headerText = "ctpHeader";
        ctHeader.setStringValue( headerText );
        XWPFParagraph headerParagraph = new XWPFParagraph( ctpHeader, document );
        //设置为右对齐
        headerParagraph.setAlignment( ParagraphAlignment.RIGHT );
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader( XWPFHeaderFooterPolicy.DEFAULT, parsHeader );

        //添加页脚
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        String footerText = "ctpFooter";
        ctFooter.setStringValue( footerText );
        XWPFParagraph footerParagraph = new XWPFParagraph( ctpFooter, document );
        headerParagraph.setAlignment( ParagraphAlignment.CENTER );
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter( XWPFHeaderFooterPolicy.DEFAULT, parsFooter );

        document.write( out );
        out.close();
    }

    @Test
    public void poi2() throws IOException {
//解析docx模板并获取document对象
        XWPFDocument document = new XWPFDocument( POIXMLDocument.openPackage( "C:\\\\Users\\\\haya\\\\Desktop\\\\word (2).docx" ) );
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph item : paragraphs) {
            System.out.println( item.getRuns() );
        }
    }

    @Test
    public void poi3() throws IOException {
        Configure config = Configure
                .newBuilder()
                .customPolicy( "progress", new CompetitionApplyPolicy() )
                .build();
        XWPFTemplate.compile( "C:\\Users\\haya\\Desktop\\word (2).docx", config )
                .render( new HashMap<String, Object>() {{
                    put( "name", "蓝桥杯" );
                    put( "progress", new LinkedList<Object>() {{
                        add( new Progress() {{
                            setName( "1" );
                        }} );
                        add( new Progress() {{
                            setName( "2" );
                        }} );
                        add( new Progress() {{
                            setName( "3" );
                        }} );
                        add( new Progress() {{
                            setName( "4" );
                        }} );
                        add( new Progress() {{
                            setName( "5" );
                        }} );
                    }} );
                }} ).writeToFile( "C:\\Users\\haya\\Desktop\\out_template.docx" );
    }

    @Test
    public void poi4() throws IOException {
        Competition competition = competitionMapper.getWithBudgetListById( 98 );

        Map<String, Object> props = new HashMap<String, Object>(){{
            put( "name", competition.getName() );
            put( "budget", competition.getProgressList() );
        }};
        Configure config = Configure
                .newBuilder()
                .customPolicy( "budget", new BudgetPolicy() )
                .build();
        XWPFTemplate.compile( "C:\\Users\\haya\\Desktop\\budget.docx", config )
                .render(props)
                .writeToFile( "C:\\Users\\haya\\Desktop\\out_template.docx" );
    }
}
