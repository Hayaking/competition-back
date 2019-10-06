package cadc.util;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * @author haya
 */
public class ExcelUtils {

    public static boolean generateExcel(String name, String sheetName, List<?> data, Class<?> clazz) {
        if (sheetName == null || StringUtils.isBlank( sheetName )) {
            sheetName = "sheet1";
        }
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String outPath = root + name + ".xlsx";
        EasyExcel.write( outPath, clazz ).sheet( sheetName ).doWrite( data );
        return true;
    }
}
