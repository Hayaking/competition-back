package cadc.bean.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author haya
 */
@Data
public class Demo {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("整数标题")
    private Integer num;
    @ExcelProperty("小数标题")
    private Double doubleNum;

    public Demo() {
    }

    public Demo(String string, Integer num, Double doubleNum) {
        this.string = string;
        this.num = num;
        this.doubleNum = doubleNum;
    }
}
