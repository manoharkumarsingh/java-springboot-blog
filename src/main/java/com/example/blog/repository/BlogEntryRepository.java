package com.example.blog.repository;

import com.example.blog.entity.BlogEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlogEntryRepository  extends MongoRepository<BlogEntry, ObjectId> {

    List<BlogEntry> findAllByUserId(ObjectId userId);
}
