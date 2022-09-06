package nl.first8.jpa;

import lombok.extern.slf4j.Slf4j;
import nl.first8.jpa.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class Jpa02_AssociationTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private CustomerJpaRepository customerRepository;

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
    @DisplayName("1. association not used")
    void lazyAssociationNotUsed() {


        for (Customer customer : customerRepository.findAll()) {
            log.info("customer {}", customer);
        }

    }

    @Test
    @DisplayName("2. why N+1 when books association used - lazy vs eager")
    void whyNplusOneSelectionWhenBooksAssociationUsed() {

        // books are needed
        for (Customer customer : customerRepository.findAll()) {
            log.info("customer {} has {} books", customer, customer.getBooks().size());
        }

        // check the SQL log: N+1 selection issue

        // change Customer.books FetchType to EAGER

        // same? not entirely - additional query still performed but only timing is different
    }

    @Test
    @DisplayName("3. why doesn't FetchMode.EAGER work?")
    void whyDoesntFetchModeEagerWork() {

        customerRepository.findAll().forEach(customer -> log.info("customer {}", customer));

        // books are needed
        Optional<Customer> foundById = customerRepository.findById(5L);
        foundById.ifPresent(customer -> log.info("customer {} has {} books", customer, customer.getBooks().size()));

        // check the SQL log: single query!

        // why? fetch strategy only used when finding by id
        // Spring Data ignores fetch mode
    }

    @Test
    @DisplayName("4. use join fetch with the entity manager")
    void useJoinFetchWithEntityManager() {

        List<Customer> customers = entityManager
                .createQuery("SELECT c FROM Customer c LEFT JOIN FETCH c.books", Customer.class)
                .getResultList();

        for (Customer customer : customers) {
            log.info("customer {} has {} books", customer, customer.getBooks().size());
        }

        //
        // check the SQL log: single query, but: duplicates

        // -> Possible solution: Set instead of List
        // -> Possible solution: "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.books"

    }



    @Autowired
    private CustomerJpaWithFetchJoinRepository customerFetchJoinRepository;

    @Test
    @DisplayName("5. use join fetch with Spring Data")
    void useJoinFetchWithSpringData() {

        List<Customer> customers = customerFetchJoinRepository.findAll();

        for (Customer customer : customers) {
            log.info("customer {} has {} books", customer, customer.getBooks().size());
        }

        //
        // check the SQL log: same duplicates as using entity manager, but now query defined on repo

        // Duplicates considered 'by design' by JPA Specification 4.4.5.3 Fetch Joins:
        //
        //      SELECT d
        //      FROM Department d LEFT JOIN FETCH d.employees WHERE d.deptno = 1
        //
        //      A fetch join has the same join semantics as the corresponding inner or outer join, [...]. Hence,
        //      for example, if department 1 has five employees, the above query returns five references to the
        //      department 1 entity.
        //
        // See: https://github.com/spring-projects/spring-data-jpa/issues/1623

    }



}
