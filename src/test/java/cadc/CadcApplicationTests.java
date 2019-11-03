package cadc;

import cadc.bean.excel.Demo;
import cadc.entity.Join;
import cadc.entity.Menu1;
import cadc.entity.Menu2;
import cadc.entity.RoleMenu;
import cadc.mapper.Menu2Mapper;
import cadc.service.JoinService;
import cadc.service.Menu1Service;
import cadc.service.Menu2Service;
import cadc.service.RoleMenuService;
import cadc.util.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class CadcApplicationTests {
    @Autowired
    public Menu1Service menu1Service;
    @Autowired
    public Menu2Service menu2Service;
    @Autowired
    public RoleMenuService roleMenuService;
    @Resource
    public Menu2Mapper menu2Mapper;

    @Autowired
    public JoinService joinService;

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
            menu1s.add( menu1Service.getById( item.getMenu1Id() ) );
        }
        System.out.println(menu1s);
    }

//    @Test
//    public void getMenu2ByIdList() {
//        LinkedList<Integer> list = new LinkedList<>();
//        list.add( 1 );
//        list.add( 2 );
//        List<Menu2> list1 = menu2Mapper.getByIdList( list );
//        System.out.println( list1 );
//    }

    @Test
    public void getEnterList() {
        IPage<Join> enterList = joinService.getEnterList( new Page<>( 1, 12 ), 82, 2 );
        log.warn( enterList );

    }
}
