package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import java.util.UUID;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
}
