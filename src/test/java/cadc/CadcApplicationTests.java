package cadc;

import cadc.bean.excel.Demo;
import cadc.util.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadcApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void simpleWrite() {
        // 写法1D
        List<Demo> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add( new Demo( i + "", i, (double) i ) );
        }
        ExcelUtils.generateExcel( "haya", "", list, Demo.class );

    }
}
