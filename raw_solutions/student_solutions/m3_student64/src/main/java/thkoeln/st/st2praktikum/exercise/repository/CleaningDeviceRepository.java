package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Space;

import java.util.List;
import java.util.UUID;

@Service
public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {
    List<CleaningDevice> findBySpace(Space space);
}
