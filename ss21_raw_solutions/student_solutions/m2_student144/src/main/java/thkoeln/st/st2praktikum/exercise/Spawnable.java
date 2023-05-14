package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Spawnable {
    public Boolean spawnRobot(UUID currentRoomId, Boolean spawnPointBlocked);
}
