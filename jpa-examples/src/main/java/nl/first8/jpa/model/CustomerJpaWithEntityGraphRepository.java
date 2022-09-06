package nl.first8.jpa.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerJpaWithEntityGraphRepository extends JpaRepository<Customer, Long> {

    @Query("FROM Customer")
    @EntityGraph(attributePaths = "books") // ad-hoc
    List<Customer> findAllWithBooks();

    @Query("FROM Customer")
    @EntityGraph(attributePaths = { "books", "books.reviews" }) // ad-hoc
    List<Customer> findAllWithBooksAndReviews();

    @EntityGraph(attributePaths = "books") // ad-hoc
    List<Customer> findAllBySurname(String surname);

    @EntityGraph(value = "Customer.books")
    List<Customer> findAllByName(String name);

    @EntityGraph(value = "Customer.books")
    Optional<Customer> findByName(String name);
}
