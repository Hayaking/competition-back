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
    @TableField("cp_name")
    private String name;

    @TableField("cp_start_time")
    private Date startTime;
    @TableField("cp_end_time")
    private Date endTime;
    @TableField("cp_enter_start_time")
    private Date enterStartTime;
    @TableField("cp_enter_end_time")
    private Date enterEndTime;

    @TableField("cp_group_num")
    private String groupNum;
    @TableField("cp_stu_num")
    private int stuNum;
    @TableField("cp_ex_res")
    private String exRes;
    @TableField("cp_place")
    private String place;
    @TableField("cp_org")
    private String org;
    @TableField("cp_co_org")
    private String coOrg;

    @TableField("cp_state")
    private String state;
    @TableField("cp_enter_state")
    private String enterState;
    @TableField("cp_start_state")
    private String startState;

    @TableField("cp_person_in_charge")
    private String personInCharge;
    @TableField("cp_creater")
    private String creater;
    @TableField("cp_highest_level")
    private String highestLevel;
    @TableField("cp_process")
    private String process;
    @TableField("cp_intro")
    private String intro;
    @TableField("type_id")
    private int type;
    @TableField("teacher_group_id")
    private int groupId;
    private int typeJoinId;
}
