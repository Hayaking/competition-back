package cadc.bean.word;

import cadc.bean.holder.Cost;
import cadc.entity.Progress;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.util.LinkedList;
import java.util.List;

@Log4j2
public class CostPolicy extends DynamicTableRenderPolicy {
    // 人工费填充数据所在行数
    int rowLine = 7;
    int offset = 0;

    public CostPolicy() {
    }

    public CostPolicy(int offset) {
        this.offset = offset;
    }

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == data) return;
        List<Cost> list = (List<Cost>) data;
        List<Object> props;
        for (int i = 0; i < list.size(); i++) {
            Cost item = list.get( i );
            props = new LinkedList<Object>() {{
                add( "" );
                add( item.getSubject() );
                add( "" );
                add( item.getValue() );
                add( item.getReason() );
                add( "" );
            }};
            if (i == 0) table.removeRow( rowLine + offset);
            XWPFTableRow row = table.insertNewTableRow( rowLine + offset + i );
            for (int j = 0; j < 6; j++) {
                XWPFTableCell cell = row.createCell();
                if (j == 0) {
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal( STMerge.CONTINUE );
                    continue;
                }
                cell.setText( String.valueOf( props.get( j ) ) );
            }
            TableTools.mergeCellsHorizonal( table, rowLine + offset + i, 1, 2 );
            TableTools.mergeCellsHorizonal( table, rowLine + offset + i, 3, 4 );
        }
    }

}
