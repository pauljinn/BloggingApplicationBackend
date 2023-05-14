package com.enc.blog.services;

import java.util.List;

import com.enc.blog.payloads.CategoryDto;

public interface CategoryService {
	//Create
	CategoryDto creteCategory(CategoryDto categoryDto);
	
	//Read
	List<CategoryDto> getCategories();
	
	CategoryDto getCategory(Integer categoryId);
	
	//Update 
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	
	//Delete
	void deleteCategory(Integer categoryId);

}
