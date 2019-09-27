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
@TableName("teacher_group")
public class TeacherGroup extends Model<TeacherGroup> {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private int creatorId;
    private String state;
    private String createTime;
    private String reason;

    @TableField(exist = false)
    private Teacher creator;

    public TeacherGroup() {
    }

    public TeacherGroup(String name) {
        this.name = name;
    }

    public TeacherGroup(String name, int creatorId, String state, String createTime) {
        this.name = name;
        this.creatorId = creatorId;
        this.state = state;
        this.createTime = createTime;
    }
}
