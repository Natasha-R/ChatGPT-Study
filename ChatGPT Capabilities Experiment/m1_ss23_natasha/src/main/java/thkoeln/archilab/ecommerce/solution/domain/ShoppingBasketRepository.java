package thkoeln.archilab.ecommerce.solution.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.ShoppingBasket;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, UUID> {
    Optional<ShoppingBasket> findByCustomer(Customer customer);
}
