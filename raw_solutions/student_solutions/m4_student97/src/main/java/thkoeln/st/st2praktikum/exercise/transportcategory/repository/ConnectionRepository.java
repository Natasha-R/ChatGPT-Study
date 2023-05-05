package thkoeln.st.st2praktikum.exercise.transportcategory.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;

import java.util.Optional;
import java.util.UUID;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    Optional<Connection> findConnectionsByStartRoomAndStartPositionAndEndRoom(Room startRoom, Point startPosition, Room endRoom);
}
