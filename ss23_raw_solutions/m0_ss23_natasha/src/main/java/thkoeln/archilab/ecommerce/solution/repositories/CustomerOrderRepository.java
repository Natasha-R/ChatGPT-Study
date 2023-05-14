package thkoeln.archilab.ecommerce.solution.repositories;

import thkoeln.archilab.ecommerce.solution.domain.CustomerOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, UUID> {}