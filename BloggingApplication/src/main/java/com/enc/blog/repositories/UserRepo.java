package com.enc.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enc.blog.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

}
