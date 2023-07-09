package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.FieldConnection;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldConnectionRepo extends CrudRepository<FieldConnection, UUID> {

    Optional<FieldConnection> findBySource_IdAndDestination_Id(UUID source, UUID destination);
}
