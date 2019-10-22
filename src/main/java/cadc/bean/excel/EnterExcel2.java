package cadc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author haya
 */
@Data
@Builder
public class EnterExcel2 implements ExcelModel {
    @ExcelProperty("#")
    private Integer index;
    @ExcelProperty("参赛人员")
    private String member;
    @ExcelProperty("指导老师1")
    private String lead1;
}
