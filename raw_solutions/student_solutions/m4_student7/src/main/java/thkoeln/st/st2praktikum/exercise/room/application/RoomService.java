package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RoomService {
    private List<Room> rooms = new ArrayList<>();

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
        Room room = new Room(height, width);
        rooms.add(room);
        persistNewData();
        return room.getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = getRoomByRoomId(roomId);
        if(room.isCoordinateInBounds(wall.getStart()) && room.isCoordinateInBounds(wall.getEnd())) {
            room.addBlocker(wall);
            persistNewData();
        }
        else
            throw new UnsupportedOperationException("wall out of bounds");
    }
    public Room getRoomByRoomId(UUID roomId){
        return rooms.stream().filter(findRoom -> findRoom.getId().equals(roomId)).findAny().orElse(null);
    }

    public void persistNewData(){
        roomRepository.deleteAll();

        rooms.forEach(room -> roomRepository.save(room));
    }

}
