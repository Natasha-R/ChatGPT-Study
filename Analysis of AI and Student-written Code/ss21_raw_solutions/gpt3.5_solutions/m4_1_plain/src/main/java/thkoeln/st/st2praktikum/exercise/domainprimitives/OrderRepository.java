package thkoeln.st.st2praktikum.exercise.domainprimitives;

import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroid;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    void delete(Order order);
    List<Order> findByOrderType(OrderType orderType);

    public static void deleteAll(List<Order> maintenanceOrders) {
        maintenanceOrders.forEach(order -> order.setMaintenanceDroid(null));
        maintenanceOrders.clear();
    }
    public static List<Order> findByMaintenanceDroid(MaintenanceDroid maintenanceDroid) {
        EntityManager entityManager = null;
        return entityManager.createQuery("SELECT o FROM Order o WHERE o.maintenanceDroid = :maintenanceDroid", Order.class)
                .setParameter("maintenanceDroid", maintenanceDroid)
                .getResultList();
    }
}

