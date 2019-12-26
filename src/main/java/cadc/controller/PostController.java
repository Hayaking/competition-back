package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Post;
import cadc.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author syh
 * @Date 2019/11/7
 */
@Log4j2
@RestController
public class PostController {
    @Autowired
    PostService postService;

    @RequestMapping(value = "/post/{id}" ,method = RequestMethod.GET)
    public Object getById(@PathVariable int  id){
        Post post = postService.getById(id);
        return MessageFactory.message(post);
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public Object addPost(@RequestBody Post post) {
        Boolean flag = postService.addPost(post);
        return MessageFactory.message(flag);
    }
    @RequestMapping(value = "/post/del/{id}", method = RequestMethod.GET)
    public Object deletePost(@PathVariable int id) {
        Boolean flag = postService.deletePostById(id);
        return MessageFactory.message(flag);
    }
    @RequestMapping(value = "post/top3")
    public Object getTopPost(){
        List<Post> list = postService.getPostTop3();
        return MessageFactory.message(list);
    }

}
