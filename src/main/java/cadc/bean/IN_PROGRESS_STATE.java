package cadc.bean;

public enum IN_PROGRESS_STATE {
    PROMOTION("晋级",1),
    NO_PROMOTION("未晋级",0),

    REVIEWED("已审核", 1),
    NO_REVIEW("可被编辑|等待审核",0),

    PRICE("得奖", 1),
    NO_PRICE("未得奖", 0);

    private String str;
    private int code;

    IN_PROGRESS_STATE(String str, int code) {
        this.str = str;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
