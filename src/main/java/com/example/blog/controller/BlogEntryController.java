package com.example.blog.controller;

import com.example.blog.dto.BlogEntryRequest;
import com.example.blog.entity.BlogEntry;
import com.example.blog.service.BlogEntryService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createBlogEntry(
            @Valid @RequestBody BlogEntryRequest request,
            Authentication authentication
    ) {
        User user = userService.findByEmail(authentication.getName());
        if(user == null){
            return  ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authenticated user not found");
        }
        BlogEntry blogEntry = new BlogEntry();
        blogEntry.setTitle(request.getTitle());
        blogEntry.setContent(request.getContent());
        blogEntry.setUserId(user.getId());
        blogEntryService.saveEntry(blogEntry);
        return ResponseEntity.ok(blogEntry);
    }

    @GetMapping
    public ResponseEntity<?> getAllBlog() {
       List<BlogEntry> allBlog = blogEntryService.getAllBlogEntries();
       if( allBlog != null && !allBlog.isEmpty()){
           return new ResponseEntity<>(allBlog, HttpStatus.OK);
       }
       return ResponseEntity.notFound().build();
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteBlogEntryById(@PathVariable ObjectId myId) {
        boolean res = blogEntryService.deleteById(myId);
        if(!res){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Blog entry with id " + myId + " not found");
        }
        return ResponseEntity.ok("Blog entry deleted successfully");
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId) {
        if( blogEntryService.getBlogEntryById(myId) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Blog entry with id " + myId + " not found");
        }
        return ResponseEntity.ok(blogEntryService.getBlogEntryById(myId));
    }

    @PutMapping
    public ResponseEntity<?> updateBlog(@RequestBody BlogEntry blogEntry) {
        ObjectId id = blogEntry.getId();
        if(blogEntryService.getBlogEntryById(id) == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Blog entry with id " + id + " not found");
        }
        return ResponseEntity.ok(blogEntryService.updateBlogEntry(blogEntry));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBlogEntry() {
        blogEntryService.deleteAllBlogEntries();
        return ResponseEntity.ok("Blog entry deleted successfully");
    }
}
