package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.room.Room;

import java.util.HashMap;
import java.util.UUID;

public class RoomRepo {
    private final HashMap<UUID, Room> rooms;

    public void add(Room newRoom) {
        rooms.put(newRoom.getId(), newRoom);
    }
    public void removeById(UUID roomId) {
        rooms.remove(roomId);
    }

    public Room findById(UUID roomId) {
        return rooms.get(roomId);
    }

    public RoomRepo() {
        this.rooms = new HashMap<>();
    }
}
