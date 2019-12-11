package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("price")
public class Price extends Model<Price> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int typeId;
    private Date priceTime;
    private String priceState;
    private int joinId;
    private int joinInProgressId;
    private int state;

    @TableField(exist = false)
    private Join join;
    @TableField(exist = false)
    private PriceType type;
}
