package thkoeln.archilab.ecommerce.solution.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.Delivery;

import java.util.List;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    List<Delivery> findAllByCustomerId(UUID customerId);
}

