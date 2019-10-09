package cadc.util;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

import java.io.*;
import java.util.List;

/**
 * @author haya
 */
public class ExcelUtils {

    public static boolean generateExcel(String name, String sheetName, List<?> data, Class<?> clazz) {
        if (sheetName == null || StringUtils.isBlank( sheetName )) {
            sheetName = "sheet1";
        }
        try {
            String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
            String outPath = root + "static/excel/" + name + ".xlsx";
            File outFile = new File( outPath );
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
            }
            EasyExcel.write( outPath, clazz ).sheet( sheetName ).doWrite( data );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static InputStream getExcel(String name) {
        String root = ClassUtils.getDefaultClassLoader().getResource( "" ).getPath();
        String path = root + "static/excel/" + name + ".xlsx";
        File file = new File( path );
        FileInputStream in = null;
        try {
            in = new FileInputStream( file );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }
}
