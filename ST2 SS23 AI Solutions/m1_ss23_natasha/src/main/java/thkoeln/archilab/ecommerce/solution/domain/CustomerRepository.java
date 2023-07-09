package thkoeln.archilab.ecommerce.solution.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    Optional<Customer> findByMailAddress(String mailAddress);
    Optional<Customer> findByNameAndStreetAndCityAndPostalCode(String name, String street, String city, String postalCode);

}
