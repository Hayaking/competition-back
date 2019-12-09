package cadc.mapper;

import cadc.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author syh
 * @Date 2019/11/7
 */
public interface PostMapper extends BaseMapper<Post> {

    @Results(value = {
            @Result(column = "name_space",property = "nameSpace"),
            @Result(column = "creator", property = "creatorId"),
            @Result(column = "create_time",property = "createTime")
        }
    )
    @Select("select * from post where id = #{id}")
    Post getById(@Param("id") int id);




}
