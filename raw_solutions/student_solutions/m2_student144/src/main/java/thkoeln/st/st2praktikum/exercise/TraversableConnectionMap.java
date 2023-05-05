package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TraversableConnectionMap {
    static Map<UUID,TraversableConnection> traversableConnectionMap = new HashMap<>();

    static public void addToMap(TraversableConnection traversableConnection, UUID sourceRoomId){
        traversableConnectionMap.put(sourceRoomId,traversableConnection);
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

    static public boolean findConnectionOnPoint(String robotCurrentPosition, UUID currentRoomId){
        if(TraversableConnectionMap.findConnectionInRoom(currentRoomId).getSourcePoint().getCurrentPosition().equals(robotCurrentPosition)){
            return true;
        }
        else return false;
    }
}
