package thkoeln.st.st2praktikum.exercise.room.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.OutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;



import java.util.UUID;


@Service
public class RoomService {

    @Getter
    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        return roomRepository.save(newRoom).getUuid();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = roomRepository.findByUuid(roomId);
        if(room.isInsideLimit(wall.getStart()) && room.isInsideLimit(wall.getEnd())) {
            room.addWall(wall);
        }else{
            throw new OutOfBoundsException("Wall is placed out of bounds.");
        }
    }
}
