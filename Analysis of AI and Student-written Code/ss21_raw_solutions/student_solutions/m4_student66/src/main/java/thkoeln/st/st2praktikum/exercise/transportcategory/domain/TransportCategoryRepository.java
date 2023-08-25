package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import java.util.List;
import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
    TransportCategory findByUuid(UUID uuid);
    List<TransportCategory> findByConnectionsSourceRoomUuidAndConnectionsSourcePoint(UUID uuid, Point point);
}
