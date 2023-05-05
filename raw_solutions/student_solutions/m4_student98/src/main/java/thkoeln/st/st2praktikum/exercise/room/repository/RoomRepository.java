package thkoeln.st.st2praktikum.exercise.room.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {
}
