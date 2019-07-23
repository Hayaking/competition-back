package cadc.bean.message;

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
}
