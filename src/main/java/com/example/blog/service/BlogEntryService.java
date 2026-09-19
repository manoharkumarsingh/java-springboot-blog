package com.example.blog.service;

import com.example.blog.entity.BlogEntry;
import com.example.blog.repository.BlogEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogEntryService {
    @Autowired
    private BlogEntryRepository blogEntryRepository;

    public void saveEntry(BlogEntry blogEntry) {
        blogEntryRepository.save(blogEntry);
    }

    public List<BlogEntry> getAllBlogEntries() {
        return blogEntryRepository.findAll();
    }

    public boolean deleteById(ObjectId myId) {
        if(blogEntryRepository.existsById(myId)) {
            blogEntryRepository.deleteById(myId);
            return true;
        }
        return false;
    }

    public BlogEntry getBlogEntryById(ObjectId myId) {
        return blogEntryRepository.findById(myId).orElse(null);
    }

    public BlogEntry updateBlogEntry(BlogEntry blogEntry) {
        return blogEntryRepository.save(blogEntry);
    }
}
