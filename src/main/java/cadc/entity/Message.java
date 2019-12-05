package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("message")
public class Message extends Model<Message> {
    @TableId(type = IdType.AUTO)
    private int id;
    private int code;
    private String body;
    @TableField(value = "`to`")
    private String to;
    @TableField(value = "`from`")
    private String from;
    private Boolean isRead;
    private Boolean isToStudent;
    private Boolean isFromStudent;
    private String extra;
    private Date createTime;

    public Message() {
    }

    public Message(String body) {
        this.body = body;
    }
}
