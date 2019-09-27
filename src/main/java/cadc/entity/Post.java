package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("permission_menu")
public class Post {
    private int id;
    private String nameSpace;
    private String title;
    private String body;
}
