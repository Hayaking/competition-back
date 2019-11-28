package cadc.bean.word;

import cadc.entity.Progress;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.LinkedList;
import java.util.List;

@Log4j2
public class BudgetPolicy extends DynamicTableRenderPolicy {
    // 人工费填充数据所在行数
    int rowLine = 1;

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == data) return;
        List<Progress> list = (List<Progress>) data;
        List<Object> props;
        for (int i = 0; i < list.size(); i++) {
            Progress item = list.get( i );
            props = new LinkedList<Object>() {{
                add( item.getName() );
                add( item.getType().getTypeName() );
                add( item.getBudget().getEnter() );
                add( item.getBudget().getTravel() );
                add( item.getBudget().getThing() );
                add( item.getBudget().getOther() );
                add( item.getBudget().getReason() );
                add( item.getBudget().getEnter() + item.getBudget().getTravel() + item.getBudget().getThing() + item.getBudget().getOther() );
            }};
            if (i == 0) table.removeRow( rowLine );
            XWPFTableRow row = table.insertNewTableRow( rowLine + i );
            for (int j = 0; j < 8; j++) {
                XWPFTableCell cell = row.createCell();
                cell.setText( String.valueOf( props.get( j ) ) );
            }
        }
    }

}
