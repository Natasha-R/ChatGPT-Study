package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;


import java.util.UUID;


@Service
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        return roomRepository.save(room).getId();
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        roomRepository.findById(roomId).get().addObstacle(obstacle);
    }
}
