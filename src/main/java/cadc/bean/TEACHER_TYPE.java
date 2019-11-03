package cadc.bean;

public enum TEACHER_TYPE {
    TACHER_ADMIN(2),
    TEACHER_NORMAL(1),
    TEACHER_LEAD(4);

    private int val;
    TEACHER_TYPE(int i) {
        this.val = i;
    }

    public int getVal() {
        return val;
    }
}
