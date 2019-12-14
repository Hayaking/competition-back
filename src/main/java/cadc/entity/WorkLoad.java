package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "workload")
public class WorkLoad extends Model<WorkLoad> {
    private int id;
    private Double val;
    private int joinId;
    private int teacherId;

    @TableField(exist = false)
    private Join join;
}
