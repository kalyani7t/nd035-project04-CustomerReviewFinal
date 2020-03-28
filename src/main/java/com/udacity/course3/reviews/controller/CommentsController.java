package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.CommentDoc;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDoc;
import com.udacity.course3.reviews.repository.jpa.CommentRepository;
import com.udacity.course3.reviews.repository.jpa.ReviewRepository;
import com.udacity.course3.reviews.repository.mongodb.ReviewDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewDocRepository reviewDocRepository;

    public CommentsController(ReviewRepository reviewRepository, CommentRepository commentRepository, ReviewDocRepository reviewDocRepository) {
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
        this.reviewDocRepository = reviewDocRepository;
    }

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()){
            Review review = optionalReview.get();
            comment.setReview(review);
            //Persist in MongoDB as well
            ReviewDoc matchingReviewDoc = reviewDocRepository.findById(review.getReviewDocId()).get();
            List<CommentDoc> commentDocs = matchingReviewDoc.getComments();
            CommentDoc commentDoc = new CommentDoc();
            commentDoc.setTitle(comment.getTitle());
            commentDoc.setCommentText(comment.getCommentText());
            commentDocs.add(commentDoc);
            reviewDocRepository.save(matchingReviewDoc);
            return ResponseEntity.ok(commentRepository.save(comment));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()){
            return ResponseEntity.ok(commentRepository.findAllByReview(new Review(reviewId)));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}