package nl.first8.jpa;

import lombok.extern.slf4j.Slf4j;
import nl.first8.jpa.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@DataJpaTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
class Jpa04_ProjectionTests {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private CustomerJpaRepository customerRepository;

    @Autowired
    private BookJpaRepository bookRepository;

    @BeforeEach
    void setUp() {

        customerRepository.saveAllAndFlush(Arrays.asList(
                new Customer("John", "Smith", new Address("1234AB", "Amsterdam"), bookRepository.saveAll(Arrays.asList(
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
    @DisplayName("1. why do we need all properties")
    void entities() {

        Collection<Customer> customers = customerRepository.findBySurname("Smith");

        for (Customer customer : customers) {
            log.info("customer {} lives in {}", customer.getName(), customer.getAddress().getCity());
        }

        // check the SQL log: all Customer properties are loaded - even those we don't need

        // Address is loaded lazily - when association is touched

        // Can we improve?
    }








    @Autowired
    private CustomerWithInterfaceBasedProjectionRepository customerRepositoryWithInterfaces;

    @Test
    @DisplayName("2. interface-based projection")
    void useInterfaceBasedProjection() {

        Collection<CustomerSummary> proxies = customerRepositoryWithInterfaces.findBySurname("Smith");

        for (CustomerSummary customer : proxies) {
            log.info("customer {} lives in {}", customer.getName(), customer.getAddress().getCity());
        }

        // check the SQL log:
        // - nice! query has been optimized with just the needed properties of Customer
        // - nice! address has been join fetched: single query!

        // however, all Address properties are still loaded due to
        // https://github.com/spring-projects/spring-data-jpa/issues/1555
    }

    @Autowired
    private CustomerWithClassBasedProjectionRepository customerRepositoryWithClasses;

    @Test
    @DisplayName("3. class-based projection")
    void useClassBasedProjection() {

        Collection<NamesOnlyDto> dtos = customerRepositoryWithClasses.findBySurname("Smith");

        for (NamesOnlyDto customer : dtos) {
            log.info("customer {}", customer.getName());
        }

        // check the SQL log:
        // - nice! query has been optimized with just the needed properties of Customer
        // - no managed entity, should be 40% faster according to https://thorben-janssen.com/entities-dtos-use-projection/
    }

}
