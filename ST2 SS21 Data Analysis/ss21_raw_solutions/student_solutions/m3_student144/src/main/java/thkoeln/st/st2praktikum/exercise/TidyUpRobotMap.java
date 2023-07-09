package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class TidyUpRobotMap {
    static Map<UUID, TidyUpRobot> tidyUpRobotMap = new HashMap<>();

    static public void addToMap(TidyUpRobot tidyUpRobot, UUID tidyUpRobotId) {
        tidyUpRobotMap.put(tidyUpRobotId, tidyUpRobot);
    }

    static public TidyUpRobot getByUUID(UUID tidyUpRobotId){
        return tidyUpRobotMap.get(tidyUpRobotId);
    }
    //gets unmoved robot in spawnroom
    static  public  Boolean unMovedRobot(UUID roomId){
        for (Map.Entry<UUID, TidyUpRobot> entry : tidyUpRobotMap.entrySet()){
            if(entry.getValue().getIsSpawned().equals(true)) {
                if (entry.getValue().getRoomId().equals(roomId) && entry.getValue().getHasMoved().equals(false)) {
                    return true;
                }
            }
        }
        return false;
    }
}
