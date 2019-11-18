package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("progress")
public class Progress extends Model<Progress> {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private int competitionId;
    private int typeId;
    private String startState;
    private String enterState;

    private Date startTime;
    private Date endTime;
    private Date enterStartTime;
    private Date enterEndTime;

    private Boolean isScanStartState;
    private Boolean isScanEnterState;
    private Boolean isSubmitResult;
    private int isReviewResult;
    private Boolean isSingle;
    private Boolean isNeedWorks;
    private String org;
    private String coOrg;
    private String place;

    @TableField(exist = false)
    private Budget budget;
    @TableField(exist = false)
    private Competition competition;
    @TableField(exist = false)
    private CompetitionType type;

}
