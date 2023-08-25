package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;
import thkoeln.st.st2praktikum.exercise.room.domain.WallMap;
import thkoeln.st.st2praktikum.exercise.room.domain.WallRepository;


import java.util.UUID;


@Service
public class RoomService {

    private RoomRepository roomRepository;
    private WallRepository wallRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, WallRepository wallRepository)
    {
        this.roomRepository = roomRepository;



        this. wallRepository = wallRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */


    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height,width);
        roomRepository.save(room);
        RoomMap.addToMap(room,room.getRoomId());
        return room.getRoomId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        wall.placeWallInRoom(roomId);
        wallRepository.save(wall);
        WallMap.addToMap(wall, wall.getWallId());
    }
}
