package thkoeln.st.st2praktikum.exercise.transportsystem.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystem;

import java.util.UUID;

public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {

}
