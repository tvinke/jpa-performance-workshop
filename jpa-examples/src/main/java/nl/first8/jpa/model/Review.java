package nl.first8.jpa.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false, updatable = false)
    private Book book;

    protected Review() {}

    public Review(int rating, String title) {
        this.title = title;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return String.format("Review[id=%d, ratinng=%s, title='%s']", id, rating, title);
    }
}
