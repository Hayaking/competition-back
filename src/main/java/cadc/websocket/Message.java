package cadc.websocket;

import lombok.Data;

/**
 * @author haya
 */
@Data
public class Message {
    private int code;
    private String from;
    private String to;
    private String body;
}
