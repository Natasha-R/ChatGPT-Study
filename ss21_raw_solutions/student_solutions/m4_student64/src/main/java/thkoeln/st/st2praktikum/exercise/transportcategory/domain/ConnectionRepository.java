package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.List;
import java.util.UUID;

@Service
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    List<Connection> findByStartSpaceId(UUID startSpaceId);
}
