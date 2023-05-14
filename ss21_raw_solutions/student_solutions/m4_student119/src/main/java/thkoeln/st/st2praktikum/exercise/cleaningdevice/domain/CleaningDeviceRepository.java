package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {
    Optional<CleaningDevice> findById(UUID uuid);
}
