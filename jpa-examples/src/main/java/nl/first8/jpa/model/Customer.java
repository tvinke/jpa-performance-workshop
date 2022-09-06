package nl.first8.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NamedEntityGraph(name = "Customer.books",
        attributeNodes = @NamedAttributeNode("books"))
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Integer age;

    @ManyToMany
    private List<Book> books = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ElementCollection
    private Set<String> nickNames = new HashSet<>();

    protected Customer() {
    }

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Customer(String name, String surname, Address address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public Customer(String name, String surname, List<Book> books) {
        this.name = name;
        this.surname = surname;
        this.books.addAll(books);
    }

    public Customer(String name, String surname, Address address, List<Book> books) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.books.addAll(books);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Address getAddress() {
        return address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books.addAll(books);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Set<String> getNickNames() {
        return nickNames;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, name='%s', surname='%s']", id, name, surname);
    }
}
