package cadc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("process")
public class Process extends Model<Process> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String description;
    private Date time;
    private String persons;
    private int competitionId;
    private int progressId;
    private int picId;
}
