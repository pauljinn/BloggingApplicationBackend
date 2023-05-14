package com.enc.blog.services;

import java.util.List;

import com.enc.blog.entities.Post;
import com.enc.blog.payloads.PostDto;

public interface PostService {
	//Create post
	Post create(PostDto postDto, Long userId, Integer categoryId);
	
	//Get Posts
	List<Post> getAllPosts();
	
	//Get Post by ID.
	Post getPostById(Integer postId);
	
	//Get all Post by similar category titles 
	List<List<Post>> getPostsByCategory(String categoryTitle);
	
	//Get all post by user.
	List<Post> getPostsByUser(Long userId);
	
	//Search posts by using keyword
	List<Post> searchPosts(String keyword);
	
	//Update Post.
	Post updatePost(PostDto postDto, Integer postId);
	
	//Delete Post
	void deletePost(Integer postId);
}
