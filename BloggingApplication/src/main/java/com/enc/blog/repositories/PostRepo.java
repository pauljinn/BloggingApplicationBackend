package com.enc.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enc.blog.entities.Category;
import com.enc.blog.entities.Post;
import com.enc.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>{
	Optional<List<Post>> findByUser(User user);
	List<Post> findByCategory(Category category);
	Optional<List<Post>> findByContentContaining(String keyword);
}