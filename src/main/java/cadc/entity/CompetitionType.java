package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author haya
 */
@Data
@TableName("type_competition")
public class CompetitionType {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("type_name")
    private String typeName;
}
