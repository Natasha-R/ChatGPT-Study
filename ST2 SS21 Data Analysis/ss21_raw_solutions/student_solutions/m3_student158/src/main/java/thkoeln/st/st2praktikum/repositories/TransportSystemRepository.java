package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportSystem;

import java.util.UUID;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {

}
