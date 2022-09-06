package nl.first8.jpa;

import lombok.extern.slf4j.Slf4j;
import nl.first8.jpa.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class Jpa03_EntityGraphTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private CustomerJpaWithEntityGraphRepository customerRepository;

    @Autowired
    private BookJpaRepository bookRepository;

    @BeforeEach
    void setUp() {

        customerRepository.saveAllAndFlush(Arrays.asList(
                new Customer("John", "Smith", bookRepository.saveAll(Arrays.asList(
                        new Book("Where the Crawdads Sing"),
                        new Book("The Return: Trump's Big 2024 Comeback"),
                        new Book("The Seven Husbands of Evelyn Hugo: A Novel")
                ))),
                new Customer("Anne", "Jones", bookRepository.saveAll(Arrays.asList(
                        new Book("November 9: A Novel")
                )))
        ));
        entityManager.clear();
        log.info(">>>>>>>>>>>>");
    }


    @Test
    @DisplayName("1. use ad-hoc entity graphs")
    void useAdhocEntityGraph() {

        Consumer<Customer> printIt = customer -> log.info("customer {} has {} books", customer.getName(), customer.getBooks().size());

        customerRepository.findAllWithBooks().forEach(printIt);

        customerRepository.findAllWithBooks().forEach(printIt);

        customerRepository.findAllWithBooksAndReviews().forEach(printIt);

        customerRepository.findAllBySurname("Smith").forEach(printIt);

    }

    @Test
    @DisplayName("2. use named entity graphs")
    void useNamedEntityGraph() {

        Consumer<Customer> printIt = customer -> log.info("customer {} has {} books", customer.getName(), customer.getBooks().size());

        customerRepository.findAllByName("John").forEach(printIt);

        customerRepository.findByName("John").ifPresent(printIt);

        // or what about subgraphs
//        @NamedEntityGraph(name = "Customer.all",
//                attributeNodes = @NamedAttributeNode(value = "books", subgraph = "Book.reviews"),
//                subgraphs = @NamedSubgraph(name = "Book.reviews",
//                        attributeNodes = @NamedAttributeNode("reviews")))
    }

}
