package thkoeln.st.st2praktikum.exercise.transportSystem;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {
}
