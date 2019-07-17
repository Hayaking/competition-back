package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("permission_menu")
public class PermissionMenu {
    @TableId
    private int id;
    private int permissionId;
    private int menuId;

    public PermissionMenu() {
    }

    public PermissionMenu(int permissionId, int menuId) {
        this.permissionId = permissionId;
        this.menuId = menuId;
    }
}
