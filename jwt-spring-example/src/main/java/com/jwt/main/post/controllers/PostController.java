package com.jwt.main.post.controllers;

import com.jwt.main.post.models.Post;
import com.jwt.main.post.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("")
    public Post createPost(
            @RequestBody @Valid final Post post,
            final Principal principal) {
        return postService.createPost(post, principal.getName());
    }

    @GetMapping("")
    public List<Post> createPost(final Principal principal) {
        return postService.getPosts(principal.getName());
    }

//    @PutMapping("/{postId}")
//    public Task updateTask(@PathVariable final String taskId,
//                           @RequestBody @Valid final Task task) {
//        return postService.update(taskId, task);
//    }
//
//    @DeleteMapping("/{post}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteTask(@PathVariable final String taskId) {
//        postService.delete(taskId);
//    }

}
