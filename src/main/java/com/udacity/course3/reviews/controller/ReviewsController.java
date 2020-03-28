package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDoc;
import com.udacity.course3.reviews.repository.jpa.ProductRepository;
import com.udacity.course3.reviews.repository.mongodb.ReviewDocRepository;
import com.udacity.course3.reviews.repository.jpa.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewDocRepository reviewDocRepository;

    public ReviewsController(ProductRepository productRepository, ReviewRepository reviewRepository, ReviewDocRepository reviewDocRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.reviewDocRepository = reviewDocRepository;
    }

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            review.setProduct(product);
            //Replicate data in Mongodb
            ReviewDoc reviewDoc = new ReviewDoc();
            reviewDoc.setTitle(review.getTitle());
            reviewDoc.setReviewText(review.getReviewText());
            reviewDoc.setProductId(productId);
            reviewDoc.setComments(new ArrayList<>());
            reviewDocRepository.insert(reviewDoc);
            //save mongoDB id in mysql for efficient search in case of replication
            review.setReviewDocId(reviewDoc.get_id());
            return ResponseEntity.ok(reviewRepository.save(review));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDoc>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewDocRepository.findAllByProductId(productId));
    }
}