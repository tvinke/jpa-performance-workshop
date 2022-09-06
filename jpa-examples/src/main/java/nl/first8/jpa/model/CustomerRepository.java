package nl.first8.jpa.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CustomerRepository extends Repository<Customer, Long> {

    void save(Customer customer);

    List<Customer> findAll();

    List<Customer> findBySurname(String surname);

    Customer findById(Long id);

    long countBySurname(String surname);

    @Query("SELECT c FROM Customer c where c.age < 21")
    List<Customer> findYoungCustomers();
}
