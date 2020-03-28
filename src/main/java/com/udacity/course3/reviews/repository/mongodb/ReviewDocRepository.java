package com.udacity.course3.reviews.repository.mongodb;

import com.udacity.course3.reviews.entity.ReviewDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDocRepository extends MongoRepository<ReviewDoc, String>{
    List<ReviewDoc> findAllByProductId(Integer productId);
}
