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

    @CrossOrigin
    @RequestMapping(value = "/initialize", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addBlogs() {
        List<String> blogs = new ArrayList<>();
        blogs.add("blog test 1");
        blogs.add("blog test 2");
        blogs.add("blog test 3");

        for (String blog : blogs) {
            createBlog(blog);
        }
        return new ResponseEntity(blogRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBlogs() {
        return new ResponseEntity(blogRepository.findAll(), HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @RequestMapping("/update")
    public ResponseEntity<?> edit(@PathVariable String id, @RequestParam String content, @RequestParam Integer likes) {

        Optional<Blog> blog = blogRepository.findById(id);

        if (blog == null || !blog.isPresent() || blog.get() == null) {
            return new ResponseEntity("Blog " + id + " does not exist", HttpStatus.NOT_FOUND);
        }

        blog.get().setContent(content);
        blog.get().setLikes(likes);
        blogRepository.save(blog.get());

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @RequestMapping(value = "/create", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createBlog(@RequestParam String content) {
        if (alreadyAdded(content)) {
            System.out.println("Already added : " + content);
            return new ResponseEntity("Already added", HttpStatus.CONFLICT);
        } else {
            Blog blog = new Blog();
            blog.setContent(content);
            blog.setLikes(0);
            blogRepository.save(blog);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> likeBlog(@RequestParam String id) {
        Optional<Blog> blog = blogRepository.findById(id);

        if (blog == null || !blog.isPresent() || blog.get() == null) {
            return new ResponseEntity("Blog " + id + " does not exist", HttpStatus.NOT_FOUND);
        }

        blog.get().setLikes(blog.get().getLikes() + 1);
        blogRepository.save(blog.get());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteBlog(String id) {
        Optional<Blog> blog = blogRepository.findById(id);

        if (blog == null || !blog.isPresent() || blog.get() == null) {
            return new ResponseEntity("Blog " + id + " does not exist", HttpStatus.NOT_FOUND);
        }

        blogRepository.delete(blog.get());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private boolean alreadyAdded(String blog) {
        for (Blog b : blogRepository.findAll()) {
            if (blog.toLowerCase().equals(b.getContent().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
