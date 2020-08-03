package com.shepherd.blog.demo.controllers;

import com.shepherd.blog.demo.models.Blog;
import com.shepherd.blog.demo.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogController {
    @Autowired
    BlogRepository blogRepository;

    @RequestMapping("/test")
    public String helloWorld() {
        return "Hello World!!";
    }

    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?>  getBlogs() {
        List<Blog> list = new ArrayList<>();
        list.add(new Blog("1", "a", 1));
        list.add(new Blog("2", "b", 2));
        list.add(new Blog("3", "c", 3));
        //blogRepository.findAll()
        return new ResponseEntity(blogRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @RequestMapping("/update")
    public ResponseEntity<?>  edit(@PathVariable String id, @RequestParam String content, @RequestParam Integer likes) {

        Optional<Blog> blog = blogRepository.findById(id);
        blog.get().setContent(content);
        blog.get().setLikes(likes);
        blogRepository.save(blog.get());

        return new ResponseEntity(blog.get(), HttpStatus.ACCEPTED);
    }

    @RequestMapping("/create")
    @ResponseBody
    public ResponseEntity<?>  createBlog(@RequestParam String content) {
        Blog blog = new Blog();
        blog.setContent(content);
        blogRepository.save(blog);
        return new ResponseEntity("list", HttpStatus.ACCEPTED);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteBlog(String id) {
        Optional<Blog> blog = blogRepository.findById(id);
        blogRepository.delete(blog.get());
        return new ResponseEntity("list", HttpStatus.ACCEPTED);
    }
}
