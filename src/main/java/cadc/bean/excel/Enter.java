package cadc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author haya
 */
@Data
public class Enter {
    @ExcelProperty("#")
    private Integer index;
    @ExcelProperty("作品名")
    private String worksName;
    @ExcelProperty("参赛人员")
    private List<String> member;
    @ExcelProperty("指导老师1")
    private String lead1;
}
