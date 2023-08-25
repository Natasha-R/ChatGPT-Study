package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportSystem;

import java.util.List;
import java.util.UUID;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {
    List<TransportSystem> findAll();

}
