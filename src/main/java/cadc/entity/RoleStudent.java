package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("role_stu")
public class RoleStudent {
    @TableId(type = IdType.AUTO)
    private int id;
    private int stuId;
    private int roleId;

    public RoleStudent() {
    }

    public RoleStudent(int stuId, int roleId) {
        this.stuId = stuId;
        this.roleId = roleId;
    }
}
