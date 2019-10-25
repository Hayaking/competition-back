package cadc.bean.holder;

import cadc.entity.Join;
import cadc.entity.StudentGroup;
import cadc.entity.Works;
import lombok.Data;

import java.util.List;

/**
 * 封装四个对象，用于解决spring mvc无法接受多个对象的问题
 * @author haya
 */
@Data
public class EnterHolder {
    // 学生小组
    private StudentGroup group;
    // 小组成员
    private List<String> list;
    // 作品
    private Works works;
    // 参赛信息
    private Join join;
}
