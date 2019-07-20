package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("role_teacher")
public class RoleTeacher {
    private int id;
    private String teacherId;
    private int roleId;

    public RoleTeacher() {
    }

    public RoleTeacher(String teacherId, int roleId) {
        this.teacherId = teacherId;
        this.roleId = roleId;
    }
}
