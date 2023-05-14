package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {

    List<Room> findAll();

}
