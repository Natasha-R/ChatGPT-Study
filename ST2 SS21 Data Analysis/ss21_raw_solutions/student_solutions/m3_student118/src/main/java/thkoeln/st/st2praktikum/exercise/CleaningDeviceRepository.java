package thkoeln.st.st2praktikum.exercise;

import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Space;

import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {

    CleaningDevice findCleaningDeviceById(UUID cleaningDevice);

    CleaningDevice getCleaningDeviceById(UUID cleaningDevice);
    CleaningDevice findCleaningDeviceByName(String name);
}
