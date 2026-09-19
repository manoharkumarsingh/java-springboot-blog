package com.example.blog.controller;

import com.example.blog.entity.BlogEntry;
import com.example.blog.service.BlogEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;

    @PostMapping
    public ResponseEntity<BlogEntry> createBlogEntry(@RequestBody BlogEntry blogEntry) {
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
}
