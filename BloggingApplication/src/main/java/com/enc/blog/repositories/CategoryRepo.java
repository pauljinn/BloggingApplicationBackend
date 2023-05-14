package com.enc.blog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enc.blog.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{
	Optional<List<Category>> findByCategoryTitleContaining(String categoryTitle);
}
