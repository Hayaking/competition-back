package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("menu_children")
public class MenuChild {
    @TableId
    private int id;
    @TableField("menu_father")
    private String menuFather;
    @TableField("menu_child")
    private String menuChild;
}
