package com.enc.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enc.blog.entities.Category;
import com.enc.blog.entities.Post;
import com.enc.blog.entities.User;
import com.enc.blog.exceptions.ResourceNotFoundException;
import com.enc.blog.payloads.CategoryDto;
import com.enc.blog.payloads.PostDto;
import com.enc.blog.payloads.UserDto;
import com.enc.blog.repositories.CategoryRepo;
import com.enc.blog.repositories.PostRepo;
import com.enc.blog.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Post create(PostDto postDto, Long userId, Integer categoryId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
				"Can\'t create Post because user with given ID " + userId + " not found"));
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(
				"Can\'t create Post because category with given ID " + categoryId + " not found"));
		UserDto userDto = mapper.map(user, UserDto.class);
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

		Post mappedPost = mapper.map(postDto, Post.class);

		mappedPost.setImageName("default.png");
		mappedPost.setAddedDate(new Date());
		mappedPost.setUser(user);
		mappedPost.setCategory(category);
		Post savedPost = postRepository.save(mappedPost);
		return savedPost;
	}

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(Integer postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post with ID " + postId + " not found"));
	}

	/**
	 * Generic post finder by category which gives all the post whose category title is like the incoming category title.
	 */
	@Override
	public List<List<Post>> getPostsByCategory(String categoryTitle) {
		List<Category> categoriesFetched = categoryRepo.findByCategoryTitleContaining(categoryTitle)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Category with title " + categoryTitle + " not found and posts can't be fetched"));
		List<List<Post>> posts = categoriesFetched.stream().map((category)->postRepository.findByCategory(category)).collect(Collectors.toList());
		return posts;
	}

	@Override
	public List<Post> getPostsByUser(Long userId) {
		User userFound = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID " + userId + " not found while fetching posts of user"));
		List<Post> posts = postRepository.findByUser(userFound).orElseThrow(()-> new ResourceNotFoundException("No post found for user ID " + userId));
		return posts;
	}

	@Override
	public List<Post> searchPosts(String keyword) {
		List<Post> postsFound = postRepository.findByContentContaining(keyword).orElseThrow(()->new ResourceNotFoundException("No post found for keyword " + keyword));
		if(postsFound.size()==0) {
			throw new ResourceNotFoundException("No post found for keyword " + keyword);
		}
		return postsFound;
	}

	@Override
	public Post updatePost(PostDto postDto, Integer postId) {
		Post postFound = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("No posts exist for ID " + postId));
		Post mappedDto = mapper.map(postDto, Post.class);
		postFound.setTitle(mappedDto.getTitle());
		postFound.setContent(mappedDto.getContent());
		System.out.println("Coming till here");
		Post updatedPost= postRepository.save(postFound);
		return updatedPost;
	}

	@Override
	public void deletePost(Integer postId) {
		Post postFound = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("No posts exist for ID " + postId));
		postRepository.deleteById(postId);

	}

}
