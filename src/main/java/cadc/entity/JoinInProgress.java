package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("join_in_progress")
public class JoinInProgress extends Model<JoinInProgress> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int progressId;
    private int joinId;

//    private String state;
//    private Boolean isPrice;

    private Boolean promotionState;
    private Boolean priceState;
    private Boolean reviewState;
    private Boolean enterState;
    private Boolean joinState;
    private Boolean editState;

    @TableField(exist = false)
    private Progress progress;
    @TableField(exist = false)
    private Join join;
    @TableField(exist = false)
    private Price price;

}
