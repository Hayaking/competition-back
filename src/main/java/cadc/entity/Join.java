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
@TableName("`join`")
public class Join extends Model<Join> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int worksId;
    private int competitionId;
    private int teacherId1;
    private int teacherId2;
    private int groupId;
    private String applyState;
    private String applyState2;
    private String enterState;
    private String joinState;
    private int joinTypeId;
    private int creatorId;
    private Date createTime;

    @TableField(exist = false)
    private Student creator;
    @TableField(exist = false)
    private Works works;
    @TableField(exist = false)
    private Competition competition;
    @TableField(exist = false)
    private Teacher teacher1;
    @TableField(exist = false)
    private Teacher teacher2;
    @TableField(exist = false)
    private JoinInProgress inProgress;

}
