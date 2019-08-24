package cadc.bean;

public enum TEACHER_TYPE {
    TACHER_ADMIN(3),
    TEACHER_NORMAL(2),
    TEACHER_LEAD(10);

    private int val;
    TEACHER_TYPE(int i) {
        this.val = i;
    }

    public int getVal() {
        return val;
    }
}
