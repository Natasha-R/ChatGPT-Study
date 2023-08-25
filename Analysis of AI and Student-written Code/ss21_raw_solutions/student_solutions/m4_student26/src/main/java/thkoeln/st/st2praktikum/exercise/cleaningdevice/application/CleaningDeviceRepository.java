package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {
}
