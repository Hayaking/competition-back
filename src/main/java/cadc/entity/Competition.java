package cadc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("competition")
public class Competition extends Model<Competition> {
    @TableId(type = IdType.AUTO)
    private int id;
    private String name;
    private int exGroupNum;
    private int exStuNum;
    private String exRes;
    private String prePrice;
    private int state;
    private String process;
    private String intro;
    private String exCondition;
    private int personInChargeId;
    private int creatorId;
    private int teacherGroupId;
    private Boolean isNeedWorks;
    private Date createTime;

    @TableField(exist = false)
    private List<Progress> progressList;
    @TableField(exist = false)
    private Teacher personInCharge;
    @TableField(exist = false)
    private Teacher creator;

}
