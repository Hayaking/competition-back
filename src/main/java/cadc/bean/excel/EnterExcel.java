package cadc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author haya
 */
@Data
@Builder
public class EnterExcel implements ExcelModel{
    @ExcelProperty("#")
    private Integer index;
    @ExcelProperty("作品名")
    private String worksName;
    @ExcelProperty("参赛人员")
    private String member;
    @ExcelProperty("指导老师1")
    private String lead1;

    public EnterExcel() {
    }

    public EnterExcel(Integer index, String worksName,String member, String lead1) {
        this.index = index;
        this.worksName = worksName;
        this.member = member;
        this.lead1 = lead1;
    }
}
