package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author haya
 */
@Data
@TableName("competition")
public class Competition {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private Date startTime;
    private Date endTime;
    private Date enterStartTime;
    private Date enterEndTime;
    private String groupNum;
    private int stuNum;
    private String exRes;
    private String place;
    private String org;
    private String coOrg;
    private String state;
    private String enterState;
    private String startState;
    private String personInCharge;
    private String creator;
    private String highestLevel;
    private String process;
    private String intro;
    private int typeId;
    private int teacherGroupId;
    private int typeJoinId;
}
