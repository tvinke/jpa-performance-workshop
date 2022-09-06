package nl.first8.jpa;

import lombok.extern.slf4j.Slf4j;
import nl.first8.jpa.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class Jpa01_IntroductionTests {

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        customerJpaRepository.saveAllAndFlush(Collections.singletonList(
                new Customer("John", "Smith")
        ));
        entityManager.clear();
        log.info(">>>>>>>>>>>>");
    }


    @Test
    @DisplayName("1. using entity manager")
    void usingEntityManager() {

        // persist a customer
        entityManager.persist(new Customer("John", "Smith"));

        // find all customers
        List<Customer> customers = entityManager
                .createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();

        customers.forEach(customer -> log.info("customer {}", customer));

        // find customers by surname
        String surname = "Smith";
        List<Customer> customersBySurname = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.surname = :surname", Customer.class)
                .setParameter("surname", surname)
                .getResultList();

        customersBySurname.forEach(customer -> log.info("customer by surname {}", customer));

        // find customer by id
        long id = 1;
        Customer customerById = entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.id = :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();
        log.info("customer by id {}", customerById);
    }






    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("2. using Spring Repository")
    void usingSpringRepository() {

        // persist a customer
        customerRepository.save(new Customer("John", "Smith"));

        // find all customers
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(customer -> log.info("customer {}", customer));

        // find customers by surname
        List<Customer> customersBySurname = customerRepository.findBySurname("Smith");
        customersBySurname.forEach(customer -> log.info("customer by surname {}", customer));

        // find customer by id
        Customer customerById = customerRepository.findById(1L);
        log.info("customer by id {}", customerById);
    }




    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Test
    @DisplayName("3. using Spring JPA Repository")
    void usingSpringJpaRepository() {

        // save a customer
        customerJpaRepository.save(new Customer("John", "Smith"));

        // find all customers
        List<Customer> customers = customerJpaRepository.findAll();
        customers.forEach(customer -> log.info("customer {}", customer));

        // find customers by surname
        List<Customer> customersBySurname = customerJpaRepository.findBySurname("Smith");
        customersBySurname.forEach(customer -> log.info("customer by surname {}", customer));

        // find customer by id
        Optional<Customer> customerById = customerJpaRepository.findById(1L);
        log.info("customer by id {}", customerById);
    }
}
