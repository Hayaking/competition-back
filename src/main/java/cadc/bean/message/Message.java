package cadc.bean.message;

import cadc.bean.message.STATE;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haya
 */
@Data
public class Message<T>{
    STATE state;
    T body;

    public Message() {
    }

    public Message(STATE state, T body) {
        this.state = state;
        this.body = body;
    }
    public Message(STATE state) {
        this.state = state;
    }
}
