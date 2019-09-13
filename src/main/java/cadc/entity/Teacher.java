package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * @author haya
 */
@Data
@TableName("teacher")
public class Teacher  extends Model<Teacher>  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String account;
    private String password;
    private String teacherName;
    private String teacherSex;
    private String teacherPhone;
    private String teacherMaster;
    private String teacherLevel;
    private String teacherBankCardNo;
    private int picId;
    @TableField(exist = false)
    private String state;
}
