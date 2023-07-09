package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface RoomRepository extends CrudRepository<Room, UUID> {
    Room findRoomByOccupiers(TidyUpRobot robot);
    //List<Room> findRoomsByOccupiers(TidyUpRobot robot);
}