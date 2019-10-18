package cadc;

import cadc.bean.excel.Demo;
import cadc.entity.Menu1;
import cadc.entity.RoleMenu;
import cadc.service.Menu1Service;
import cadc.service.Menu2Service;
import cadc.service.RoleMenuService;
import cadc.util.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadcApplicationTests {
    @Autowired
    public Menu1Service menu1Service;
    @Autowired
    public Menu2Service menu2Service;
    @Autowired
    public RoleMenuService roleMenuService;

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

    @Test
    public void getMenu() {
        List<RoleMenu> roleMenus = roleMenuService.getListByRoleId( 1 );
        List<Menu1> menu1s = new LinkedList<>();
        System.out.println(roleMenus);
        for (RoleMenu item : roleMenus) {
            menu1s.add( menu1Service.getById( item.getMenuId() ) );
        }
        System.out.println(menu1s);
    }
}
