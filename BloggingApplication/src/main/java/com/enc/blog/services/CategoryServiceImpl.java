package com.enc.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enc.blog.entities.Category;
import com.enc.blog.exceptions.ResourceNotFoundException;
import com.enc.blog.payloads.CategoryDto;
import com.enc.blog.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryDto creteCategory(CategoryDto categoryDto) {
		Category categorySaved = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
		return modelMapper.map(categorySaved, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> allCategories = categoryRepository.findAll();
		List<CategoryDto> allCategoriesDto = allCategories.stream()
				.map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategoriesDto;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId){
		if(categoryExist(categoryId)) {
			Category categoryFound = categoryRepository.findById(categoryId).get();
			return modelMapper.map(categoryFound, CategoryDto.class);
		}
		else {
			throw new ResourceNotFoundException("Category with ID " + categoryId + " doesn't exist");
		}
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
		if(categoryExist(categoryId)) {
			return creteCategory(categoryDto);
		}
		throw new ResourceNotFoundException("Category with ID " + categoryDto.getCategoryId() + " doesn't exist"); 
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		if(categoryExist(categoryId)) {
			categoryRepository.deleteById(categoryId);
			return;
		}
		throw new ResourceNotFoundException("Category with ID " + categoryId + " doesn't exist"); 

	}
	
	
	public boolean categoryExist(Integer categoryId) {
		boolean exist = categoryRepository.existsById(categoryId);
		return exist;
	}

}
