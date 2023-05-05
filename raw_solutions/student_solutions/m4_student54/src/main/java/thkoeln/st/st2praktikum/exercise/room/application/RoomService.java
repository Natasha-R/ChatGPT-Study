package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;


import java.util.HashMap;
import java.util.UUID;


@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private HashMap<UUID, Room> rooms;



    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        if(height <= 0 || width <= 0)
        {
            throw new IllegalArgumentException();
        }

        if(rooms == null) rooms = new HashMap<>();

        UUID uuid = UUID.randomUUID();
        Room room = new Room(uuid, width, height);
        rooms.put(uuid, room);
        roomRepository.save(room);
        return uuid;
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall)
    {
        if(roomId == null || wall == null || rooms.get(roomId) == null)
        {
            throw new IllegalArgumentException();
        }

        Room room = rooms.get(roomId);
        room.addWall(wall);
        roomRepository.deleteById(roomId);
        roomRepository.save(room);
    }
}
