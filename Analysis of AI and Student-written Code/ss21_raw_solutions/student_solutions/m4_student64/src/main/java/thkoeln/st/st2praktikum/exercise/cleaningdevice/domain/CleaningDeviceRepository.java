package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.List;
import java.util.UUID;

@Service
public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {
    List<CleaningDevice> findBySpace(Space space);
}
