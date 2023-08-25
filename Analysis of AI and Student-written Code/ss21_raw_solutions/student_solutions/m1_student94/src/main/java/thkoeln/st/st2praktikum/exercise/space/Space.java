package thkoeln.st.st2praktikum.exercise.space;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Occupied;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstaclePassable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class Space implements Walkable, SpaceManageable{
    private UUID spaceId;
    private int height;
    private int width;
    private Position initialPosition;
    private List<Occupied> occupiedList;
    private List<ObstaclePassable> obstaclesList;
    private HashMap<UUID, Connectable> connectionsList;

    public Space(int height, int width){
        this.spaceId=UUID.randomUUID();
        this.height=height;
        this.width= width;
        this.initialPosition = new Position(this,0,0);
        this.obstaclesList = new ArrayList<ObstaclePassable>();
        this.occupiedList = new ArrayList<Occupied>();
        this.connectionsList = new HashMap<UUID,Connectable>();

    }
    @Override
    public UUID getId() {
        return this.spaceId;
    }

    @Override
    public Boolean isSpaceOccupied(Position position) {
        for(Occupied occupier : occupiedList) {
            if ( occupier.occupiedSpace(position) )
                return true;
        }
        return false;
    }

    @Override
    public Boolean isInitialPositionFree() {
        if (isSpaceOccupied(this.initialPosition))
            return false;
        return true;
    }

    @Override
    public Position getInitialPosition() {
        return this.initialPosition;
    }

    @Override
    public Boolean isTransportPositionValid(Position actualPosition, Walkable targetSpace) {
        for(Connectable connection : connectionsList.values()) {
            if (actualPosition.equals(connection.getSourcePosition()) && targetSpace.equals(connection.getDestinationPosition().getSpace()) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Position getTransportDestinationPosition(Position transportPosition, Walkable  targetSpace) {
        for(Connectable connection : connectionsList.values()) {
            if (transportPosition.equals(connection.getSourcePosition()) &&  targetSpace.equals(connection.getDestinationPosition().getSpace()) ) {
                return connection.getDestinationPosition();
            }
        }
        throw new IllegalArgumentException("Not Avaliable: " + transportPosition);
    }

    @Override
    public Position getLastPosition(Position targetPosition) {
        int xLastPossibleCoordinate = Math.min(targetPosition.getX(), this.width-1);
        int yLastPossibleCoordinate = Math.min(targetPosition.getY(), this.height-1);
        if (xLastPossibleCoordinate<0) xLastPossibleCoordinate = 0;
        if (yLastPossibleCoordinate<0) yLastPossibleCoordinate = 0;
        return new Position(this,xLastPossibleCoordinate,yLastPossibleCoordinate);
    }

    @Override
    public Position getObstaclePosition(Position currentPosition, Position targetPosition) {
        Position blockPosition = targetPosition;
        for (ObstaclePassable obstacle : obstaclesList)
            blockPosition = obstacle.getUnaccessPosition(currentPosition,blockPosition);
        return blockPosition;
    }

    @Override
    public Position getOccupiedPosition(Position actualPosition, Position targetPosition) {
        Position blockedPosition=targetPosition;
        for (Occupied occupied: occupiedList){
            blockedPosition=occupied.getUnaccessPosition(actualPosition,targetPosition);
        }
        return blockedPosition;
    }

    @Override
    public void removeOccupied(Occupied occupied) {
        occupiedList.remove(occupied);
    }

    @Override
    public void addOccupied(Occupied occupied) {
        this.occupiedList.add(occupied);
    }

    @Override
    public void addObstacle(ObstaclePassable obstacle) {
        obstaclesList.add(obstacle);
    }

    @Override
    public UUID addConnection(Connectable connection) {
        this.connectionsList.put(connection.getId(),connection);
        return connection.getId() ;
    }
}
