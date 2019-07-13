package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("teacher_group")
public class TeacherGroup {
    @TableId
    private int id;
    @TableField("group_name")
    private String groupName;
    @TableField("group_creater")
    private String groupCreater;
    @TableField("group_state")
    private String groupState;
    @TableField("group_create_time")
    private String groupCreateTime;
}
