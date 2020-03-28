package com.udacity.course3.reviews.entity;

import javax.persistence.*;

@Entity
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;
    @Column(name = "review_text")
    private String reviewText;
    @Column(name = "review_doc_id")
    private String reviewDocId;

    @ManyToOne
    private Product product;

    public Review() {
    }

    public Review(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getReviewDocId() {
        return reviewDocId;
    }

    public void setReviewDocId(String reviewDocId) {
        this.reviewDocId = reviewDocId;
    }
}
