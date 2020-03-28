package com.udacity.course3.reviews.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

//Document corresponds to a row in RDBMS and collection to table .. unlike @Entity which corresponds to table and it's instances to row
@Document(collection = "reviews")
public class ReviewDoc {
    /*
        Skip id with @Id as a field here use _id instead
        MongoDB will generate one for itself (_id).. it maintains order of documents internally it seems (indexes?)
        Spare yourself complication of creating id yourself. If you take responsibility of Id.. then
        while before inserting in MongoDB, you will have to take care of indexing and autoincrementing as @GeneratedValue
        won't work with MongoDB
     */
     @Id
    private ObjectId _id;

    private String title;
    private String reviewText;
    private int productId;
    private List<CommentDoc> comments;

    public ReviewDoc() {
    }


    public ReviewDoc(ObjectId _id){
        this._id = _id;
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<CommentDoc> getComments() {
        return comments;
    }

    public void setComments(List<CommentDoc> comments) {
        this.comments = comments;
    }
}

