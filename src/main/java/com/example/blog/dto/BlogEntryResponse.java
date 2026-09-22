package com.example.blog.dto;

import com.example.blog.entity.BlogEntry;
import lombok.Getter;

@Getter
public class BlogEntryResponse {
    private final String email;
    private final BlogEntry blogEntry;
    public BlogEntryResponse(String email, BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
        this.email = email;
    }
}
