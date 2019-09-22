package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("`join`")
public class Join extends Model<Join> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int worksId;
    private int competitionId;
    private int teacherId1;
    private int teacherId2;
    private String applyState;
    private String enterState;
    private String joinState;

    @TableField(exist = false)
    private Works works;
    @TableField(exist = false)
    private Competition competition;
    @TableField(exist = false)
    private Teacher teacher1;
    @TableField(exist = false)
    private Teacher teacher2;

}
