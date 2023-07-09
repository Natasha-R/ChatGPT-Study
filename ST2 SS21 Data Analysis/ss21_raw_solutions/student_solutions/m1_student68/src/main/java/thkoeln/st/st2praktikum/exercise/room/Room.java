package thkoeln.st.st2praktikum.exercise.room;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.barrier.Blockable;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.robot.Occupier;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Room implements Walkable, Buildable {
    @Id
    private UUID Id;
    private Integer width;
    private Integer height;
    private Coordinate spawnCoordinate;
    private List<Blockable> barriers;
    private List<Occupier> occupiers;
    private HashMap<UUID, Connectable> connections;

    public Room (Integer width, Integer height) {
        this.Id = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.spawnCoordinate = new Coordinate(0,0);
        this.barriers = new ArrayList<Blockable>();
        this.occupiers = new ArrayList<Occupier>();
        this.connections = new HashMap<UUID,Connectable>();
    }

    @Override
    public UUID getId() { return this.Id; }

    @Override
    public void addBarrier(Blockable barrier) {
        barriers.add(barrier);
    }

    @Override
    public UUID addConnection(Connectable connection) {
        this.connections.put(connection.getId(), connection);
        return connection.getId();
    }

    @Override
    public void addOccupier(Occupier occupier) {
        this.occupiers.add(occupier);
    }

    @Override
    public void removeOccupier(Occupier occupier) {
        occupiers.remove(occupier);
    }

    @Override
    public Boolean isSpawnCoordinateAvailable() {
        if (isFieldOccupied(this.spawnCoordinate))
            return false;
        return true;
    }

    @Override
    public Boolean isFieldOccupied (Coordinate coordinate) {
        for(Occupier occupier : occupiers) {
            if ( occupier.occupiedField(coordinate) )
                return true;
        }
    return false;
    }

    @Override
    public Coordinate getSpawnCoordinate() {
        return this.spawnCoordinate;
    }

    @Override
    public Boolean isValidTransportPosition(Coordinate transportCoordinate, Walkable targetRoom) {
        for(Connectable connection : connections.values()) {
            if (transportCoordinate.equals(connection.getSourcePosition()) && targetRoom.equals(connection.getDestinationRoom()) ) {
                return true;
            }
        }
    return false;
    }

    @Override
    public Coordinate getTransportDestinationPosition(Coordinate transportCoordinate, Walkable targetRoom) {
        for(Connectable connection : connections.values()) {
            if (transportCoordinate.equals(connection.getSourcePosition()) && targetRoom.equals(connection.getDestinationRoom()) ) {
                return connection.getDestinationPosition();
            }
        }
        throw new IllegalArgumentException("Transport-Connection is no longer available: " + transportCoordinate);
    }

    @Override
    public Coordinate getBorderBlockPosition(Coordinate targetCoordinate) {
        Integer xBlockCoordinate = Math.min(targetCoordinate.getX(), this.width-1);
        Integer yBlockCoordinate = Math.min(targetCoordinate.getY(), this.height-1);
        if (xBlockCoordinate<0) xBlockCoordinate = 0;
        if (yBlockCoordinate<0) yBlockCoordinate = 0;
        return new Coordinate(xBlockCoordinate,yBlockCoordinate);
    }

    @Override
    public Coordinate getBarrierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Coordinate blockCoordinate = targetCoordinate;
        for (Blockable barrier : barriers)
            blockCoordinate = barrier.getBlockPosition(currentCoordinate, blockCoordinate);
        return blockCoordinate;
    }

     @Override
    public Coordinate getOccupierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Coordinate blockCoordinate = targetCoordinate;
        for(Occupier occupier : occupiers) {
            blockCoordinate = occupier.getBlockPosition(currentCoordinate, blockCoordinate);
        }
        return blockCoordinate;
    }

}
