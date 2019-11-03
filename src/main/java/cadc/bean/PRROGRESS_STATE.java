package cadc.bean;

public enum PRROGRESS_STATE {
    NO_START("未开始"),
    HAD_START("已开始"),
    WAIT_END("结算中"),
    HAD_END("已结束");

    private String str;

    PRROGRESS_STATE(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
