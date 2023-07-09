package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;

import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {
}
