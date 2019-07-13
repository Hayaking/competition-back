package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("stu_group")
public class StudentGroup {
    @TableId
    private int id;
    @TableField("group_name")
    private String name;
    @TableField("group_creater")
    private String creater;
    @TableField("group_create_time")
    private String createTime;
}
