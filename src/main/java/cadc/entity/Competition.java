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
    private String groupNum;
    private int stuNum;
    private String exRes;
    private String place;
    private String org;
    private String coOrg;
    private String state;
    private String personInCharge;
    private String creator;
    private String process;
    private String intro;
    private int teacherGroupId;
    private int joinTypeId;
    private Boolean isHaveWorks;
    @TableField(exist = false)
    private List<Progress> progressList;
}
