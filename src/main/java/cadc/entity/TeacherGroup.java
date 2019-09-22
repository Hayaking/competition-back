package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private int creator;
    private String state;
    private String createTime;

    public TeacherGroup() {
    }

    public TeacherGroup(String name) {
        this.name = name;
    }

    public TeacherGroup(String name, int creator, String state, String createTime) {
        this.name = name;
        this.creator = creator;
        this.state = state;
        this.createTime = createTime;
    }
}
