package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Connection;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ConnectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class ConnectionRepositoryImpl implements ConnectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Connection connection) {
        entityManager.persist(connection);
    }

    @Override
    public Optional<Connection> findById(UUID id) {
        Connection connection = entityManager.find(Connection.class, id);
        return Optional.ofNullable(connection);
    }

    @Override
    public void delete(Connection connection) {
        entityManager.remove(connection);
    }

    @Override
    public List<Connection> findAll() {
        return entityManager.createQuery("SELECT c FROM Connection c", Connection.class).getResultList();
    }

    @Override
    public List<Connection> findBySourceSpaceShipDeckId(UUID sourceSpaceShipDeckId) {
        return entityManager.createQuery("SELECT c FROM Connection c WHERE c.sourceSpaceShipDeckId = :sourceSpaceShipDeckId", Connection.class)
                .setParameter("sourceSpaceShipDeckId", sourceSpaceShipDeckId)
                .getResultList();
    }

    @Override
    public List<Connection> findByDestinationSpaceShipDeckId(UUID destinationSpaceShipDeckId) {
        return entityManager.createQuery("SELECT c FROM Connection c WHERE c.destinationSpaceShipDeckId = :destinationSpaceShipDeckId", Connection.class)
                .setParameter("destinationSpaceShipDeckId", destinationSpaceShipDeckId)
                .getResultList();
    }

    @Override
    public List<Connection> findByTransportCategory(String transportCategory) {
        return entityManager.createQuery("SELECT c FROM Connection c WHERE c.transportCategory = :transportCategory", Connection.class)
                .setParameter("transportCategory", transportCategory)
                .getResultList();
    }

}
