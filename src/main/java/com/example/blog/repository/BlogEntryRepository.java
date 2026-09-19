package com.example.blog.repository;

import com.example.blog.entity.BlogEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogEntryRepository  extends MongoRepository<BlogEntry, ObjectId> {
}
