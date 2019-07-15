package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haya
 */
@Data
@TableName("menu")
public class Menu implements Serializable {
    @TableId
    private int id;
    private String path;
    private String name;
    @TableField(exist = false)
    private Meta meta;
    private String component;
    @TableField("meta_id")
    private Integer metaId;
    @TableField(exist = false)

    private List<Menu> children;

    public void addChild(Menu child) {
        if (this.children==null) {
            this.children = new LinkedList<>();
        }
        this.children.add( child );
    }
}
