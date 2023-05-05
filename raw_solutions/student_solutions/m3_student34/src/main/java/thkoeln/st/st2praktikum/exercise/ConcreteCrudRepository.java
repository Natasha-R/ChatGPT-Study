package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.*;

public class ConcreteCrudRepository<T extends Entity> implements CrudRepository<T,
        UUID> {

    private List<T> spaceShipDecks;

    public ConcreteCrudRepository() {
        this.spaceShipDecks = new ArrayList<>();
    }

    @Override
    public <S extends T> S save(S s) {
        this.spaceShipDecks.removeIf(it -> it.getId().equals(s.getId()));
        this.spaceShipDecks.add(s);
        return s;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<T> findById(UUID uuid) {
        return this.spaceShipDecks.stream()
                .filter(it -> it.getId().equals(uuid))
                .findAny();
    }

    @Override
    public boolean existsById(UUID uuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<T> findAll() {
        return Collections.unmodifiableList(this.spaceShipDecks);
    }

    @Override
    public Iterable<T> findAllById(Iterable<UUID> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException();
        }
        this.spaceShipDecks.removeIf(it -> it.getId().equals(uuid));
    }

    @Override
    public void delete(T spaceShipDeck) {
        if (spaceShipDeck == null) {
            throw new IllegalArgumentException();
        }
        this.deleteById(spaceShipDeck.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
