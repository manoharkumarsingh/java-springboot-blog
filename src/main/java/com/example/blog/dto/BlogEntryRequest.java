package com.example.blog.dto;

import lombok.Data;

@Data
public class BlogEntryRequest {
    private String title;
    private String content;
}