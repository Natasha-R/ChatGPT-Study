package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Movable {

    public boolean command(String commandString, HashMap<UUID,Connection> connectionHashMap, HashMap<UUID,Space> spaceHashMap);

    public boolean transport(HashMap<UUID,Connection> connectionHashMap , String destination , HashMap<UUID,Space> spaceHashMap);

    public boolean moveWest(String distanceParameter, Space space);

    public boolean moveEast(String distanceParameter, Space space);

    public boolean moveNorth(String distanceParameter, Space space);

    public boolean moveSouth(String distanceParameter, Space space);

    public boolean canCollide();

}
