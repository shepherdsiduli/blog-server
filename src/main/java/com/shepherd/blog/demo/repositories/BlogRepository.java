package com.shepherd.blog.demo.repositories;

import com.shepherd.blog.demo.models.Blog;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<Blog, String> {
    @Override
    public void delete(Blog blog);
}
