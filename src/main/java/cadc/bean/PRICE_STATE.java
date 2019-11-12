package cadc.bean;

public enum PRICE_STATE {
    NO_PASS("未通过",0),
    PASS("通过", 1),
    EDIT("可被编辑|等待审核",2);

    private String str;
    private int code;

    PRICE_STATE(String str, int code) {
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
