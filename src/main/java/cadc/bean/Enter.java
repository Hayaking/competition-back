package cadc.bean;

import cadc.entity.Join;
import cadc.entity.StudentGroup;
import cadc.entity.Works;
import lombok.Data;

import java.util.List;

/**
 * @author haya
 */
@Data
public class Enter {
    private StudentGroup group;
    private List<String> list;
    private Works works;
    private Join join;
}
