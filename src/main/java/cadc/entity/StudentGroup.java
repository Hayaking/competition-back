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
@TableName("stu_group")
public class StudentGroup extends Model<StudentGroup> {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private int creator;
    private String createTime;

    @TableField(exist = false)
    private String worksName;

    public StudentGroup() {
    }

    public StudentGroup(String name, int creator, String createTime) {
        this.name = name;
        this.creator = creator;
        this.createTime = createTime;
    }
}
