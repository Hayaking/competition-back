package cadc.service;

import cadc.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author syh
 * @Date 2019/11/7
 */
public interface PostService extends IService<Post> {
    Post getById(int id);

    boolean addPost(Post post);

    boolean deletePostById(int id);

}
