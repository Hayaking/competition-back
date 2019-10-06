package cadc.bean.message;

import static cadc.bean.message.STATE.FAILED;
import static cadc.bean.message.STATE.SUCCESS;

/**
 * @author haya
 */
public class MessageFactory {


    public static Message message() {
        return new Message();
    }

    public static <T> Message message(STATE state, T body) {
        return new Message<>( state, body );
    }

    public static <T> Message message(STATE state) {
        return new Message<>( state );
    }

    public static <T> Message message(Boolean flag) {
        return new Message<>( flag ? SUCCESS : FAILED );
    }

    public static <T> Message message(Boolean flag, T body) {
        return new Message<>( flag ? SUCCESS : FAILED, body );
    }

    public static <T> Message message( T body) {
        return message( body != null, body );
    }
}
