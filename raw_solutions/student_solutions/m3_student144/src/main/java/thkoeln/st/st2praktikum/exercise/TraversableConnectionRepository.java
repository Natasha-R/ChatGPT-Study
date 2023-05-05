package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TraversableConnectionRepository extends CrudRepository<TraversableConnection, UUID> {
}
