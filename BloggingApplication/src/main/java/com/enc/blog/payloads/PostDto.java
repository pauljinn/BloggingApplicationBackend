package com.enc.blog.payloads;

import java.util.Date;

import com.enc.blog.entities.Category;
import com.enc.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private UserDto user;
	private Date addedDate;
	private CategoryDto category;	
}
