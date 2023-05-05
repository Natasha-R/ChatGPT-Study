package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FieldConnectionRepository extends CrudRepository<FieldConnection, UUID> {

    Optional<FieldConnection> findBySource_IdAndDestination_Id(UUID source, UUID destination);
}
