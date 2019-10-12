package cadc.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * @author haya
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToken extends UsernamePasswordToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private boolean isRemember;

    public UserToken() {
    }


    public UserToken(final String username, final String password,  boolean isRemember,  String type) {
        super( username, password );
        this.type = type;
        this.isRemember = isRemember;
    }

}
