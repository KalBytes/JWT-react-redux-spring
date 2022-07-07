package com.jwt.main.post.services;

import com.jwt.main.post.models.Post;
import com.jwt.main.post.repositories.PostRepository;
import com.jwt.main.user.models.User;
import com.jwt.main.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Transactional
    public Post createPost(final Post post, final String userEmail) {
        final User user = userService.getUserByEmail(userEmail);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional
    public List<Post> getPosts(final String userEmail) {
        final User user = userService.getUserByEmail(userEmail);
        return postRepository.findByUser(user);
    }
}
