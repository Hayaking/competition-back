package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("works")
public class Works{
    @TableId(type = IdType.AUTO)
    private int id;
    private String worksName;
    private int stuGroupId;

    private StudentGroup studentGroup;
}
