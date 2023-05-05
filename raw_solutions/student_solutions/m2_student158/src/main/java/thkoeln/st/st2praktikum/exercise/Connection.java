package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection extends AbstractEntity {

    private UUID sourceSpaceId;

    private UUID destinationSpaceId;

    private Coordinate sourceSpaceCoordinate;

    private Coordinate destinationSpaceCoordinate;

    public Connection(UUID sourceSpaceId, UUID destinationSpaceId, Coordinate sourceSpaceCoordinate, Coordinate destinationSpaceCoordinate) {
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceSpaceCoordinate = sourceSpaceCoordinate;
        this.destinationSpaceCoordinate = destinationSpaceCoordinate;
        checkIfConnectionsAreVaild();
    }

    private void checkIfConnectionsAreVaild(){
        SpaceInterface sourceSpace = SpaceFactory.getSpaces().get(sourceSpaceId);
        SpaceInterface destinationSpace = SpaceFactory.getSpaces().get(sourceSpaceId);

        if(sourceSpace.getWidth()<sourceSpaceCoordinate.getX() || sourceSpace.getHeight()<sourceSpaceCoordinate.getY() ||
                destinationSpace.getWidth()<destinationSpaceCoordinate.getX() || destinationSpace.getHeight()<destinationSpaceCoordinate.getY()){
            throw new IllegalArgumentException("Connection coordinates are out bounds");
        }
    }
    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public Coordinate getSourceSpaceCoordinate() {
        return sourceSpaceCoordinate;
    }

    public Coordinate getDestinationSpaceCoordinate() {
        return destinationSpaceCoordinate;
    }

}
