package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("teacher_in_group")
public class TeacherInGroup extends Model<TeacherInGroup> {
    @TableId
    private int id;
    private int teacherId;
    private int groupId;
    private String state;

    public TeacherInGroup() {
    }

    public TeacherInGroup(int groupId, int teacherId, String state) {
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.state = state;
    }
}
