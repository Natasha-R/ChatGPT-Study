package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements Unique{

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getID() {
        return id;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    private UUID id;
    private UUID sourceSpaceId;

    private Coordinate sourceCoordinate;
    private UUID destinationSpaceId;
    private Coordinate destinationCoordinate;


    public Connection(Space sourceSpace, Coordinate sourceCoordinate, Space destinationSpace, Coordinate destinationCoordinate){

        if(sourceSpace.inBounds(sourceCoordinate) && destinationSpace.inBounds(destinationCoordinate)) {
            this.id = UUID.randomUUID();
            this.sourceSpaceId = sourceSpace.getID();
            this.destinationSpaceId = destinationSpace.getID();


            this.sourceCoordinate = sourceCoordinate;
            this.destinationCoordinate = destinationCoordinate;
        }else throw new RuntimeException("Coordinate out of Bounds");
    }


}
