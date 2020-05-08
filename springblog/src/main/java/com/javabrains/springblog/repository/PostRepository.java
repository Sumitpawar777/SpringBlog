package com.javabrains.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javabrains.springblog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
