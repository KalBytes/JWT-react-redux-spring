package com.jwt.main.post.repositories;

import com.jwt.main.post.models.Post;
import com.jwt.main.user.models.User;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @NonNull
    List<Post> findByUser(User user);
}
