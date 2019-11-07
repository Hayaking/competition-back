package cadc.bean;

/**
 * @author haya
 */

public enum JOIN_STATE {
    NO_START("未开始"),
    HAD_START("已开始"),
    WAIT_END("结算中"),
    HAD_END("已结束"),
    NO_PROMOTION("未晋级"),
    PROMOTION("晋级");

    private String str;

    JOIN_STATE(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
