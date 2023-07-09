package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TraversableConnectionMap {
    static Map<UUID,TraversableConnection> traversableConnectionMap = new HashMap<>();

    static public void addToMap(TraversableConnection traversableConnection, UUID sourceRoomId){
        traversableConnectionMap.put(sourceRoomId,traversableConnection);
    }

    static public TraversableConnection getByUUID(UUID connectionId){
        return traversableConnectionMap.get(connectionId);
    }
    //gets connection in room
    static public TraversableConnection findConnectionInRoom(UUID currentRoomId){


        for(Map.Entry<UUID, TraversableConnection> entry : traversableConnectionMap.entrySet()){
            if(entry.getValue().getSourceRoomId().equals(currentRoomId)){
                return entry.getValue();
            }
        }
        return null;
    }
}
