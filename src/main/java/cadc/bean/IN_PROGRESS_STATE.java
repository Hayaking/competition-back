package cadc.bean;

public enum IN_PROGRESS_STATE {
    PROMOTION("晋级",true),
    NO_PROMOTION("未晋级",false),

    REVIEWED_PASS( "已审核", 1 ),
    REVIEWED_REFUSE( "拒绝", -1 ),
    REVIEW_WAIT("等待审核",0),

    PRICE("得奖", true),
    NO_PRICE("未得奖", false),

    ENTER_PASS("已报名",1),
    ENTER_REFUSE("拒绝",-1),
    ENTER_WAIT("等待审核",0),

    EDITABLE("可编辑",true),
    NO_EDITABLE("不可编辑",false),

    JOIN("已参赛",true),
    NO_JOIN("未参赛",false),

    FUNDED("自费",true),
    NO_FUNEDE("非资费",false);


    private String str;
    private Boolean flag;
    private Integer code;

    IN_PROGRESS_STATE(String str, Boolean flag) {
        this.str = str;
        this.flag = flag;
    }
    IN_PROGRESS_STATE(String str, Integer code) {
        this.str = str;
        this.code = code;
    }

    public Boolean getFlag() {
        return flag;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
