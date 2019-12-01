package cadc.bean.word;

import cadc.entity.Process;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class ProcessPolicy extends DynamicTableRenderPolicy {
    // 人工费填充数据所在行数
    int rowLine = 4;
    int offset = 0;

    public ProcessPolicy() {
    }

    public ProcessPolicy(int offset) {
        this.offset = offset;
    }

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == data) return;
        List<Process> list = (List<Process>) data;
        List<Object> props;
        for (int i = 0; i < list.size(); i++) {
            Process item = list.get( i );
            props = new LinkedList<Object>() {{
                add( "");
                add( new SimpleDateFormat( "yyyy年MM月dd日" ).format( item.getTime() ) );
                add( item.getDescription() );
                add( "" );
                add( "" );
                add( "" );
            }};
            if (i == 0) table.removeRow( rowLine );
            XWPFTableRow row = table.insertNewTableRow( rowLine + i );
            for (int j = 0; j < 6; j++) {
                XWPFTableCell cell = row.createCell();
                if (j == 0) {
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal( STMerge.CONTINUE );
                    continue;
                }
                cell.setText( String.valueOf( props.get( j ) ) );
            }
            TableTools.mergeCellsHorizonal( table, rowLine + i, 2, 5 );
        }
    }

}
