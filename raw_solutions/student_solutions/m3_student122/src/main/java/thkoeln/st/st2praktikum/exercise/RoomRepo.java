package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.UUID;

@Repository
public interface RoomRepo extends JpaRepository<Room,Integer> {
    Room findRoomById(UUID id);
    void deleteRoomById(UUID id);
}
