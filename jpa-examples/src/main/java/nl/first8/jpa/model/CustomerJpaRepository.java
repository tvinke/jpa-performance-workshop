package nl.first8.jpa.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

    List<Customer> findBySurname(String surname);

    long countBySurname(String surname);
}
