package thkoeln.st.st2praktikum.exercise;

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
    static public List<Wall> findAllRelevantWallsInRoom(UUID roomId,Boolean isUpDown){
        List<Wall> relevantWalls = new ArrayList<>();
        if (!isUpDown) {
            for (Map.Entry<UUID, Wall> entry : wallMap.entrySet()) {
                if (entry.getValue().roomId.equals(roomId) && !entry.getValue().isUpDown) {
                    relevantWalls.add(entry.getValue());
                }
            }
        }
        if (isUpDown) {
            for (Map.Entry<UUID, Wall> entry : wallMap.entrySet()) {
                if (entry.getValue().roomId.equals(roomId) && entry.getValue().isUpDown) {
                    relevantWalls.add(entry.getValue());
                }
            }
        }
        return relevantWalls;
    }


}
