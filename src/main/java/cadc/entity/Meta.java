package cadc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author haya
 */
@Data
public class Meta implements Serializable {
    private String title;
    private String icon;
    private boolean hideInMenu;
}
