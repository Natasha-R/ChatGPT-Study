package thkoeln.st.st2praktikum.exercise.transportsystem.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;

import java.util.List;
import java.util.UUID;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {
    List<TransportSystem> findAll();

}
