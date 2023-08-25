package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class MapInstance {

    private RoomRepository roomRepository;
    private TidyUpRobotRepository tidyUpRobotRepository;

    public MapInstance(RoomRepository roomRepository, TidyUpRobotRepository tidyUpRobotRepository) {
        this.roomRepository = roomRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
    }

    public TidyUpRobot findRobot(UUID robotID) {
        return tidyUpRobotRepository.findById(robotID).orElseThrow(() -> new EntityNotFoundException(robotID.toString()));
    }

    public Room findRoom(UUID roomID){
        return roomRepository.findById(roomID).orElseThrow(() -> new EntityNotFoundException(roomID.toString()));
    }
}
