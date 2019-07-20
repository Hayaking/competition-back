package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("teacher_in_group")
public class TeacherInGroup {
    @TableId
    private int id;
    private String teacherId;
    private int groupId;
    private String state;

    public TeacherInGroup() {
    }

    public TeacherInGroup(int groupId, String teacherId, String state) {
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.state = state;
    }
}
