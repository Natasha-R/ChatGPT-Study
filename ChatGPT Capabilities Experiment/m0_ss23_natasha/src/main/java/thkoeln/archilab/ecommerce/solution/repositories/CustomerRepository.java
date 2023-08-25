package thkoeln.archilab.ecommerce.solution.repositories;

import thkoeln.archilab.ecommerce.solution.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    Optional<Customer> findByMailAddress(String mailAddress);
}
