package com.enc.blog.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enc.blog.entities.Post;
import com.enc.blog.payloads.PostDto;
import com.enc.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	PostService postService;
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping("/{userId}/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable String userId,@PathVariable String categoryId){
		Post createdPost = postService.create(postDto, Long.parseLong(userId), Integer.parseInt(categoryId));
		PostDto createdPostDto = modelMapper.map(createdPost, PostDto.class);
		return ResponseEntity.ok(createdPostDto);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<Post> posts = postService.getAllPosts();
		List<PostDto> postsDto = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok(postsDto);
	}
	@GetMapping("/getById/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") String postId){
		Post fetchedPost = postService.getPostById(Integer.parseInt(postId));
		PostDto dto = modelMapper.map(fetchedPost, PostDto.class);
		return new ResponseEntity<PostDto>(dto,HttpStatus.FOUND);
	}
	
	@GetMapping("/categoryTitle/{categoryTitle}")
	public ResponseEntity<List<List<PostDto>>> getPostsByCategoryTitle(@PathVariable String categoryTitle){
		System.out.println("Category title " + categoryTitle);
		List<List<Post>> fetchedPost = postService.getPostsByCategory(categoryTitle);
		List<List<PostDto>> postsDto = fetchedPost.stream().map((postList)->postList.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList())).collect(Collectors.toList());
		return ResponseEntity.ok(postsDto);
	}
	
	@GetMapping("/fetchPostByUserId/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable String userId){
		List<Post> posts = postService.getPostsByUser(Long.parseLong(userId));
		List<PostDto> postsDtos = posts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.FOUND);
	}
	
	@GetMapping("/searchPosts/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByKeyword(@PathVariable("keyword") String keyword){
		List<Post> postFound = postService.searchPosts(keyword);
		List<PostDto> postsDtos = postFound.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.FOUND);
	
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable String postId,@RequestBody PostDto postDto){
		Post updatedPost = postService.updatePost(postDto, Integer.parseInt(postId));
		PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<Map<String, String>> deletePost(@PathVariable String postId){
		postService.deletePost(Integer.parseInt(postId));
		return ResponseEntity.ok(Map.of("message","Post deleted successfully"));
	}
}
