package cadc.entity;

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
@TableName("budget")
public class Budget extends Model<Budget> {
    @TableId
    private int id;
    private Double enter;
    private Double travel;
    private Double thing;
    private Double other;
    private String reason;
    private int competitionId;
}
