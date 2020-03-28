package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDoc;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
import com.udacity.course3.reviews.repository.jpa.ReviewRepository;
import com.udacity.course3.reviews.repository.mongodb.ReviewDocRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static  org.junit.jupiter.api.Assertions.assertEquals;
import static  org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReviewsControllerTests {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewDocRepository reviewDocRepository;


    @Test
    public void givenProductAndReviewRepository_whenSaveAndRetrieveEntity_thenOK(){
        productRepository.save(getProduct());
        Optional<Product> optionalProduct = productRepository.findById(1);
        Review review = getReview();
        Product product = optionalProduct.get();
        assertNotNull(product);
        review.setProduct(product);

        //Replicate data in Mongodb
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setTitle(review.getTitle());
        reviewDoc.setReviewText(review.getReviewText());
        reviewDoc.setProductId(1);
        reviewDoc.setComments(new ArrayList<>());
        reviewDocRepository.insert(reviewDoc);

        review.setReviewDocId(reviewDoc.get_id());
        reviewRepository.save(review);
        assertEquals("Prestige Cooker", product.getName());
    }

    private Review getReview(){
        Review review = new Review();
        review.setTitle("Wonderful Kitchen Tool");
        review.setReviewText("Prestige cooker is really a must have for faster and healthier cooking.");
        return review;
    }

    private Product getProduct(){
        Product product = new Product();
        product.setName("Prestige Cooker");
        product.setDescription("Jo biwi se kare pyaar, wo Prestige se kaise kare inkaar?!");
        return product;
    }

}
