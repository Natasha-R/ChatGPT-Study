package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.bytebuddy.asm.Advice.This;


public class Room {

    protected Integer width;
    protected Integer height;
    protected UUID id;
    protected List<Barrier> barriers = new ArrayList<Barrier>();
    protected HashMap<UUID, Connection> connections = new HashMap<UUID, Connection>();


    public Room (Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.id = UUID.randomUUID();
    }


    public void addBarrier (String barrierString) {
        Barrier newBarrier = new Barrier(barrierString);
        this.barriers.add(newBarrier);
    }


    public UUID addConnection (String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        Connection newConnection = new Connection(sourceCoordinate, destinationRoomId, destinationCoordinate);
        this.connections.put(newConnection.id, newConnection);
        return newConnection.id;
    }


    public Boolean routeIsBlocked (Coordinate initialCoordinate, Integer targetX, Integer targetY) {
        // Check for Room boundaries
        if (targetX > this.width - 1 || targetX < 0 || targetY > this.height - 1 || targetY < 0) return true;

        for (Barrier barrier : barriers) {
            // Check for vertical barriers on horizontal moves
            if (initialCoordinate.y == targetY && barrier.isVertical()) {
                if (barrier.startCoordinate.y <= initialCoordinate.y && initialCoordinate.y <= (barrier.endCoordinate.y - 1)) {
                    if ((initialCoordinate.x == (barrier.startCoordinate.x - 1) && targetX == barrier.startCoordinate.x) || 
                        (initialCoordinate.x == barrier.startCoordinate.x && targetX == (barrier.startCoordinate.x - 1))) {
                            return true;
                    }
                }
            } 
            // Check for horizontal barriers on vertical moves
            else if (initialCoordinate.x == targetX && !barrier.isVertical()) {
                if (barrier.startCoordinate.x <= initialCoordinate.x && initialCoordinate.x <= (barrier.endCoordinate.x - 1)) {
                    if ((initialCoordinate.y == (barrier.startCoordinate.y - 1) && targetY == barrier.startCoordinate.y || 
                        (initialCoordinate.y == barrier.startCoordinate.y && targetY == (barrier.startCoordinate.y - 1)))) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
}
