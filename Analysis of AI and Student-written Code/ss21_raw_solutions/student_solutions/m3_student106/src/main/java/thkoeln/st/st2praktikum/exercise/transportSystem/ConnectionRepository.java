package thkoeln.st.st2praktikum.exercise.transportSystem;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    List<Connection> findBySourceFieldId(UUID sourceFieldId);
}
