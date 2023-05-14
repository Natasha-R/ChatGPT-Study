package thkoeln.st.st2praktikum.exercise.cleaningdevice.repo;

import org.springframework.data.jpa.repository.Query;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.repo.NoBeanRepository;

import java.util.List;
import java.util.UUID;


public interface CleaningDeviceRepositry extends NoBeanRepository<CleaningDevice> {

    @Query("select cd from CleaningDevice cd where cd.spaceId = ?1 and cd.point = ?2")
    public List<CleaningDevice> findByPoint(UUID spaceId, Point point);

}
