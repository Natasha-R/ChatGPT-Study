package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class MaintenanceDroidRepositoryImpl implements MaintenanceDroidRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MaintenanceDroid save(MaintenanceDroid droid) {
        entityManager.persist(droid);
        return droid;
    }

    @Override
    public Optional<MaintenanceDroid> findById(Long id) {
        return Optional.ofNullable(entityManager.find(MaintenanceDroid.class, id));
    }

    @Override
    public void delete(MaintenanceDroid droid) {
        entityManager.remove(droid);
    }

    @Override
    public List<MaintenanceDroid> findAll() {
        TypedQuery<MaintenanceDroid> query = entityManager.createQuery("SELECT d FROM MaintenanceDroid d", MaintenanceDroid.class);
        return query.getResultList();
    }
}

