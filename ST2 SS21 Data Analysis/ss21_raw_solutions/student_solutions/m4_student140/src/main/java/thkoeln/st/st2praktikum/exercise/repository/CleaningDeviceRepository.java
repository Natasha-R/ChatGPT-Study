package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

import java.util.List;
import java.util.UUID;

public interface CleaningDeviceRepository extends CrudRepository<CleaningDevice, UUID> {

   @Override
   List<CleaningDevice> findAll();

}
