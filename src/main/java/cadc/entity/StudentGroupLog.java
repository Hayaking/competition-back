package cadc.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("stu_group_log")
public class StudentGroupLog extends Model<StudentGroupLog> {
    private int id;
    private String action;
    private Date createTime;
    private int operatorId;
    private int groupId;
}
