package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {
}
