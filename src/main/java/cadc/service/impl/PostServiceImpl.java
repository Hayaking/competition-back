package cadc.service.impl;

import cadc.entity.Post;
import cadc.mapper.PostMapper;
import cadc.service.PostService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author nobugexist
 * @Date 2019/11/7
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper,Post> implements PostService{
    @Resource
    PostMapper postMapper;

    @Override
    public Post getById(int id) {
        return postMapper.getById(id);
    }

    @Override
    public boolean addPost(Post post) {
        return postMapper.insert(post) > 0;
    }

    @Override
    public boolean deletePostById(int id) {
        UpdateWrapper<Post> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        return postMapper.delete(wrapper) > 0;
    }

    @Override
    public List<Post> getPostTop3() {
        QueryWrapper<Post> qw = new QueryWrapper<>();
        qw.orderByDesc("create_time").last("limit 3");
        return postMapper.selectList(qw);
    }
}
