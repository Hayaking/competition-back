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
@TableName("stu_in_group")
public class StudentInGroup extends Model<StudentInGroup> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int stuId;
    private int groupId;
    private String state;

    @TableField(exist = false)
    private Student student;
    @TableField(exist = false)
    private StudentGroup group;

    public StudentInGroup() {
    }

    public StudentInGroup(int stuId, int groupId, String state) {
        this.stuId = stuId;
        this.groupId = groupId;
        this.state = state;
    }
}
