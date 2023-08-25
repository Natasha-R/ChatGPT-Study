package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Movable {

    public boolean command(Order order, HashMap<UUID,Connection> connectionHashMap, HashMap<UUID,Space> spaceHashMap);

    public boolean transport(HashMap<UUID,Connection> connectionHashMap , String destination , HashMap<UUID,Space> spaceHashMap);

    public boolean moveWest(Integer distanceParameter, Space space);

    public boolean moveEast(Integer distanceParameter, Space space);

    public boolean moveNorth(Integer distanceParameter, Space space);

    public boolean moveSouth(Integer distanceParameter, Space space);

    public boolean canCollide();

}
