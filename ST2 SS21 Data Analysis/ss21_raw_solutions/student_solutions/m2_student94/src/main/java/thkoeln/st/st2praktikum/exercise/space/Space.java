package thkoeln.st.st2praktikum.exercise.space;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.ObstaclePassable;
import thkoeln.st.st2praktikum.exercise.assembler.Assembler;
import thkoeln.st.st2praktikum.exercise.assembler.Queryable;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Occupied;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Space implements Walkable, SpaceManageable{
    private UUID spaceId;
    private int height;
    private  int width;

    private Coordinate initialCoordinate;
    private List<Occupied> occupiedList;
    private List<ObstaclePassable> obstaclesList;
    private static HashMap<UUID, Connectable> connectionsList;
    private Queryable assembler;

    public Space(int height, int width){
        this.spaceId=UUID.randomUUID();
        this.height =height;
        this.width = width;
        this.initialCoordinate = new Coordinate(0,0);
        this.obstaclesList = new ArrayList<ObstaclePassable>();
        this.occupiedList = new ArrayList<Occupied>();
        connectionsList = new HashMap<UUID,Connectable>();

    }
    @Override
    public UUID getId() {
        return this.spaceId;
    }

    @Override
    public Boolean isSpaceOccupied(Coordinate coordinate) {
        for(Occupied occupied : occupiedList) {
            if ( occupied.occupiedSpace(coordinate) )
                return true;
        }
        return false;
    }

    @Override
    public Boolean isInitialPositionFree() {
        if (isSpaceOccupied(this.initialCoordinate))
            return false;
        return true;
    }

    @Override
    public Coordinate getInitialPosition() {
        return this.initialCoordinate;
    }

    @Override
    public Boolean isTransportPositionValid(Coordinate actualPosition, Walkable targetSpace) {
        for(Connectable connection : connectionsList.values()) {
            if (actualPosition.equals(connection.getSourcePosition()) && targetSpace.equals(Assembler.getSpaceList().get(connection.getDestinationSpaceID()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Coordinate getTransportDestinationPosition(Coordinate actualPosition, Walkable  targetSpace) {
        for(Connectable connection : connectionsList.values()) {
            if (actualPosition.equals(connection.getSourcePosition()) &&  targetSpace.equals((Assembler.getSpaceList().get(connection.getDestinationSpaceID())))){
                return connection.getDestinationPosition();
            }
        }
        throw new IllegalArgumentException("Not Avaliable: " + actualPosition);
    }

    @Override
    public Coordinate getLastPosition(Coordinate targetPosition) {
        int xLastPossibleCoordinate = Math.min(targetPosition.getX(), this.width-1);
        int yLastPossibleCoordinate = Math.min(targetPosition.getY(), this.height-1);
        if (xLastPossibleCoordinate<0) xLastPossibleCoordinate = 0;
        if (yLastPossibleCoordinate<0) yLastPossibleCoordinate = 0;
        return new Coordinate(xLastPossibleCoordinate,yLastPossibleCoordinate);
    }

    @Override
    public Coordinate getObstaclePosition(Coordinate currentPosition, Coordinate targetPosition) {
        Coordinate blockPosition = targetPosition;
        for (ObstaclePassable obstacle : obstaclesList)
            blockPosition = obstacle.getUnaccessedCoordinate(currentPosition,blockPosition);
        return blockPosition;
    }

    @Override
    public Coordinate getOccupiedPosition(Coordinate actualPosition, Coordinate targetPosition) {
        Coordinate blockedPosition=targetPosition;
        for (Occupied occupied: occupiedList){
            blockedPosition=occupied.getUnaccessedCoordinate(actualPosition,targetPosition);
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
        connectionsList.put(connection.getId(),connection);
        return connection.getId() ;
    }

    public static  HashMap<UUID, Connectable> getConnectionsList() {
        return connectionsList;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
