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
@TableName("progress")
public class Progress extends Model<Progress> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int competitionId;
    private int typeId;
    private String startState;
    private String enterState;
    private Date startTime;
    private Date endTime;
    private Date enterStartTime;
    private Date enterEndTime;

    @TableField(exist = false)
    private Budget budget;
}
