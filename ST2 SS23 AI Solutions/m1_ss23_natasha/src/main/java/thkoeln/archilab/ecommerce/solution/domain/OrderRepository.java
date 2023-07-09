package thkoeln.archilab.ecommerce.solution.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.archilab.ecommerce.solution.order.domain.Order;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {}