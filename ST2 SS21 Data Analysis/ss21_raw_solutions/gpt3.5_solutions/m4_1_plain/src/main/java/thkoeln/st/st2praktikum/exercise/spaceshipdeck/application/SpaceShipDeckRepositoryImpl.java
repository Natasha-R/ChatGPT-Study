package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpaceShipDeckRepositoryImpl implements SpaceShipDeckRepository {

    private final EntityManager entityManager;

    public SpaceShipDeckRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Object save(SpaceShipDeck spaceShipDeck) {
        entityManager.getTransaction().begin();
        entityManager.persist(spaceShipDeck);
        entityManager.getTransaction().commit();
        return null;
    }

    @Override
    public Optional<SpaceShipDeck> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(SpaceShipDeck.class, id));
    }

    @Override
    public void delete(SpaceShipDeck spaceShipDeck) {
        entityManager.getTransaction().begin();
        entityManager.remove(spaceShipDeck);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends SpaceShipDeck> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SpaceShipDeck> findAll() {
        return entityManager.createQuery("SELECT s FROM SpaceShipDeck s", SpaceShipDeck.class).getResultList();
    }

    @Override
    public List<SpaceShipDeck> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SpaceShipDeck> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<SpaceShipDeck> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public <S extends SpaceShipDeck> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<SpaceShipDeck> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends SpaceShipDeck> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public <S extends SpaceShipDeck> List<S> saveAllAndFlush(Iterable<S> iterable) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<SpaceShipDeck> iterable) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SpaceShipDeck getOne(Long aLong) {
        return null;
    }

    @Override
    public SpaceShipDeck getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends SpaceShipDeck> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SpaceShipDeck> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SpaceShipDeck> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SpaceShipDeck> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SpaceShipDeck> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SpaceShipDeck> boolean exists(Example<S> example) {
        return false;
    }
}
