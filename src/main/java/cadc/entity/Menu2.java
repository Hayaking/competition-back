package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("menu2")
public class Menu2 extends Model<Menu2> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private int id;
    private String path;
    private String name;
    private int  metaId;
    private int  fatherId;
    private String component;
    private String des;

    @TableField(exist = false)
    private Meta meta;
}
