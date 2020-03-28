package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDoc;
import com.udacity.course3.reviews.repository.jpa.CommentRepository;
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
public class CommentsControllerTests {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private ReviewDocRepository reviewDocRepository;


    @Test
    public void givenProductAndReviewAndCommentRepositories_whenSaveAndRetrieveEntities_thenOK(){
        productRepository.save(getProduct());
        Optional<Product> optionalProduct = productRepository.findById(1);
        Product product = optionalProduct.get();

        Review review = getReview();
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

        Optional<Review> optionalReview = reviewRepository.findById(1);
        Review foundReview = optionalReview.get();

        Comment comment = getComment();
        assertNotNull(foundReview);
        comment.setReview(foundReview);
        commentRepository.save(comment);

        assertEquals("Wonderful Kitchen Tool", foundReview.getTitle());
    }

    private Comment getComment(){
        Comment comment = new Comment();
        comment.setTitle("Is it heavy?");
        comment.setCommentText("Is it Aluminium body or Steel body? Heavy or light?");

        return comment;
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
