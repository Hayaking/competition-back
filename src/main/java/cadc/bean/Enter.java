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
    // 学生小组
    private StudentGroup group;
    // 小组成员
    private List<String> list;
    // 作品
    private Works works;
    // 参赛信息
    private Join join;
}
