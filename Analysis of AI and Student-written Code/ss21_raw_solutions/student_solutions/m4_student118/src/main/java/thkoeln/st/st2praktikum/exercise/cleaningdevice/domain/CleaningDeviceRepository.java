package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {

    CleaningDevice findCleaningDeviceById(UUID cleaningDevice);
    void deleteById(UUID cleaningDevice);


    CleaningDevice getCleaningDeviceById(UUID cleaningDevice);
    CleaningDevice findCleaningDeviceByName(String name);
}
