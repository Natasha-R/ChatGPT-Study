package thkoeln.st.st2praktikum.exercise.room.application;

import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RoomMap {
    static Map<UUID, Room> roomMap = new HashMap<>();

    static public  void addToMap(Room room, UUID roomId){
        roomMap.put(roomId,room);
    }

    static public Room getByUUID(UUID roomId){
        return roomMap.get(roomId);
    }
}
