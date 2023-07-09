package thkoeln.st.st2praktikum.exercise.space.repo;


import org.springframework.data.jpa.repository.Query;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.repo.NoBeanRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceRepositry extends NoBeanRepository<Space> {
    @Query("select sp from Space sp join sp.cleaningDevices cd  where cd.cleaningDeviceId = ?1 ")
    public Optional<Space> findBycleaningDeviceId(UUID cleaningDeviceId);

}
