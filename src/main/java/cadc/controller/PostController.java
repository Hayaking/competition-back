package cadc.controller;

import cadc.bean.message.MessageFactory;
import cadc.entity.Post;
import cadc.service.PostService;
import cadc.service.impl.PostServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author syh
 * @Date 2019/11/7
 */
@Log4j2
@RestController
public class PostController {
    @Autowired
    PostServiceImpl postServiceimpl;

    @RequestMapping(value = "/post/{id}" ,method = RequestMethod.GET)
    public Object getById(@PathVariable int  id){
        Post post = postServiceimpl.getById(id);
        return MessageFactory.message(post);
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public Object addPost(@RequestBody Post post) {
        Boolean flag = postServiceimpl.addPost(post);
        return MessageFactory.message(flag);
    }
    @RequestMapping(value = "/post/del/{id}", method = RequestMethod.GET)
    public Object deletePost(@PathVariable int id) {
        Boolean flag = postServiceimpl.deletePostById(id);
        return MessageFactory.message(flag);
    }

}
