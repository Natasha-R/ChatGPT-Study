package thkoeln.st.st2praktikum.exercise.domainprimitives;

import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroid;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public void delete(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }
    @Override
    public List<Order> findByOrderType(OrderType orderType) {
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.orderType = :orderType", Order.class)
                .setParameter("orderType", orderType)
                .getResultList();
    }

}
