package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Connection;
import thkoeln.st.st2praktikum.exercise.Point;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    public List<Connection> findBySourceRoomIdAndSourceCoordinate(UUID sourceRoomId, Point sourceCoordinate);
}
