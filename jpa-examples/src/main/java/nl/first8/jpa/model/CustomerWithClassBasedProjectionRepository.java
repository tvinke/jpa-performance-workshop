package nl.first8.jpa.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CustomerWithClassBasedProjectionRepository extends JpaRepository<Customer, Long> {

    Collection<NamesOnlyDto> findBySurname(String lastName);

}
