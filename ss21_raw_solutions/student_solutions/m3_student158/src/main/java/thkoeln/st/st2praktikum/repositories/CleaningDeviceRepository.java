package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceInterface;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Task;

import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {

}
