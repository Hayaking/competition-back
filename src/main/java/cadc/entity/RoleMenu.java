package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_menu")
public class RoleMenu extends Model<RoleMenu> implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private int id;
    private int roleId;
    private int menu1Id;
    private int menu2Id;
}
