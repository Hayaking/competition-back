package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * @author haya
 */

@Data
@TableName("student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String account;
    private String password;
    private String stuName;
    private String stuClass;
    private String stuPhone;
    private String stuSex;
    private String stuBankCardNo;
    private int picId;

    public Student() {
    }

    public Student(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
