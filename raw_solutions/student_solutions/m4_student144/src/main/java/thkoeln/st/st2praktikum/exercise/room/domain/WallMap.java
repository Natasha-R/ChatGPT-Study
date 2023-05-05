package thkoeln.st.st2praktikum.exercise.room.domain;

import java.util.*;

public class WallMap {
    static Map<UUID,Wall> wallMap = new HashMap<>();

    static public void addToMap(Wall wall, UUID wallId){
        wallMap.put(wallId, wall);
    }

    static public Wall getByUUID(UUID wallId){
        return wallMap.get(wallId);
    }

    //find all walls relevant for current robot movement
    static public List<Wall> findAllRelevantWallsInRoom(UUID roomId,Boolean isHorizontal){
        List<Wall> relevantWalls = new ArrayList<>();
        if (!isHorizontal) {
            for (Map.Entry<UUID, Wall> entry : wallMap.entrySet()) {
                if (entry.getValue().getDestinationRoomId().equals(roomId) && !entry.getValue().getIsHorizontal()) {
                    relevantWalls.add(entry.getValue());
                }
            }
        }
        if (isHorizontal) {
            for (Map.Entry<UUID, Wall> entry : wallMap.entrySet()) {
                if (entry.getValue().getDestinationRoomId().equals(roomId) && entry.getValue().getIsHorizontal()) {
                    relevantWalls.add(entry.getValue());
                }
            }
        }
        return relevantWalls;
    }


}
