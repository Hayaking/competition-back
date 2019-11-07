package cadc.bean;

/**
 * @author haya
 */

public enum ENTER_STATE {
    APPLYING("申请中"),
    AGREE("通过"),
    REFUSE("拒绝");

    private String str;

    ENTER_STATE(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
