package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TraversableConnection;

import java.util.UUID;

public interface TraversableConnectionRepository extends CrudRepository<TraversableConnection, UUID> {
}
