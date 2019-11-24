package cadc.bean;

/**
 * @author haya
 */

public enum PROGRESS_STATE {
    NO_START("未开始",0),
    HAD_START("已开始",1),
    WAIT_END("结算中",2),
    HAD_END( "已结束", 3 ),;

    private String str;
    private Integer code;

    PROGRESS_STATE(String str, Integer code) {
        this.code = code;
        this.str = str;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
