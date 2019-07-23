package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("teacher_group")
public class TeacherGroup extends Model<TeacherGroup> {
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

    public TeacherGroup() {
    }

    public TeacherGroup(String groupName, String groupCreater, String groupState, String groupCreateTime) {
        this.groupName = groupName;
        this.groupCreater = groupCreater;
        this.groupState = groupState;
        this.groupCreateTime = groupCreateTime;
    }
}
