package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("works")
public class Works extends Model<Works> {
    @TableId(type = IdType.AUTO)
    private int id;
    private String worksName;
    private int stuGroupId;
    private int creatorId;

    @TableField(exist = false)
    private StudentGroup studentGroup;
}
