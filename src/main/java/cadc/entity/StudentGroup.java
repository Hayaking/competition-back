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
@TableName("stu_group")
public class StudentGroup extends Model<StudentGroup> {
    @TableId(type = IdType.AUTO)
    private int id;
//    @TableField("group_name")
    private String name;
//    @TableField("group_creater")
    private String creator;
//    @TableField("group_create_time")
    private String createTime;

    public StudentGroup() {
    }

    public StudentGroup(String name, String creator, String createTime) {
        this.name = name;
        this.creator = creator;
        this.createTime = createTime;
    }
}
