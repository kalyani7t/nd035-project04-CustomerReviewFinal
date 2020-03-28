package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.udacity.course3.reviews.repository.mongodb.ReviewDocRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = ReviewsApplication.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext

public class ReviewDocRepositoryTests {
	@Autowired
	private ReviewDocRepository reviewDocRepository;

	@DisplayName("Given object When save object using MongoDB template Then object can be found")

	@Test
	public void test(@Autowired MongoTemplate mongoTemplate) {
		String collectionName = "testdb";

		// given
		DBObject objectToSave = BasicDBObjectBuilder.start()
				.add("title", "test review")
				.add("reviewText","This is a test review")
				.add("productId","1")
				.get();

		// when
		mongoTemplate.save(objectToSave, collectionName);

		//then
		Assertions.assertThat(reviewDocRepository.findAllByProductId(1).contains(objectToSave));

	}


}