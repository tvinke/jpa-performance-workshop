package nl.first8.jpa.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] coverPhoto;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews = new HashSet<>();

    protected Book() {}

    public Book(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setBook(this);
    }

    @Override
    public String toString() {
        return String.format("Book[id=%d, title='%s']", id, title);
    }
}
