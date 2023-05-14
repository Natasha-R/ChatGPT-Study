package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectable;
import thkoeln.st.st2praktikum.exercise.interfaces.Buildable;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocking;
import thkoeln.st.st2praktikum.exercise.interfaces.Occupier;
import thkoeln.st.st2praktikum.exercise.interfaces.Walkable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Room implements Walkable, Buildable {
    @Id
    private UUID Id;
    private Integer width;
    private Integer height;
    private Coordinate spawnCoordinate;
    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER )
    private List<Blocking> barriers;
    @OneToMany (targetEntity = TidyUpRobot.class)
    private List<Occupier> occupiers;
    @OneToMany (targetEntity = Connection.class)
    private List<Connectable> connections;

    public Room (Integer height, Integer width) {
        this.Id = UUID.randomUUID();
        this.height = height;
        this.width = width;
        this.spawnCoordinate = new Coordinate(0,0);
        this.barriers = new ArrayList<Blocking>();
        this.occupiers = new ArrayList<Occupier>();
        this.connections = new ArrayList<Connectable>();
    }

    @Override
    public UUID getId() { return this.Id; }

    @Override
    public void addObstacle(Blocking barrier) {
        barriers.add(barrier);
    }

    @Override
    public UUID addConnection(Connectable connection) {
        this.connections.add(connection);
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
        for(Connectable connection : connections) {
            if (transportCoordinate.equals(connection.getSourcePosition()) && targetRoom.equals(connection.getDestinationRoom()) ) {
                return true;
            }
        }
    return false;
    }

    @Override
    public Coordinate getTransportDestinationPosition(Coordinate transportCoordinate, Walkable targetRoom) {
        for(Connectable connection : connections) {
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
        for (Blocking barrier : barriers)
            if (!currentCoordinate.equals(blockCoordinate))
                blockCoordinate = barrier.getBlockCoordinate(currentCoordinate, blockCoordinate);
        return blockCoordinate;
    }

     @Override
    public Coordinate getOccupierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Coordinate blockCoordinate = targetCoordinate;
        for(Occupier occupier : occupiers) {
            if (!currentCoordinate.equals(blockCoordinate))
                blockCoordinate = occupier.getBlockCoordinate(currentCoordinate, blockCoordinate);
        }
        return blockCoordinate;
    }

}
