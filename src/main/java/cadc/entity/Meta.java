package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("Meta")
public class Meta extends Model<Meta> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String icon;
    private boolean hideInMenu;
}
