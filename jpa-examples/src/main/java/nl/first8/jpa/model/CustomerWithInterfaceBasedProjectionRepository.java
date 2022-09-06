package nl.first8.jpa.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CustomerWithInterfaceBasedProjectionRepository extends JpaRepository<Customer, Long> {

    Collection<CustomerSummary> findBySurname(String lastName);

}
