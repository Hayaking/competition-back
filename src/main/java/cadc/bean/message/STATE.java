package cadc.bean.message;

/**
 * @author haya
 */

public enum STATE {

    /**
     * 成功
     */
    SUCCESS("SUCCESS"),
    /**
     * 失败
     */
    FAILED( "FAILED" ),
    TEACHER( "teacher" ),
    STUDENT( "student" ),
    // 申请立项
    STATE_COMPETITION_APPLYING("申请中"),
    STATE_COMPETITION_AGREE("通过"),
    STATE_COMPETITION_REFUSE("拒绝"),
    // 比赛开始结束。。。
    STATE_COMPETITION_NOT_START("未开始"),
    STATE_COMPETITION_HAD_START("已开始"),
    STATE_COMPETITION_END("结束"),
    STATE_INVITING( "邀请中" ),
    STATE_INVITE_FAILED( "邀请失败" ),
    STATE_INVITE_SUCCESS( "邀请成功" ),

    ;

    private String msg;

    STATE(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
