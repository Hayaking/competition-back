package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("menu")
public class Menu extends Model<Menu> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private int id;
    private String path;
    private String name;
    private Meta meta;
    private String component;

    @TableField(exist = false)
    private List<Menu> children;
}
