package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements Unique{

    public Coordinate getDestinationCoordiante() {
        return destinationCoordiante;
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
    private Coordinate destinationCoordiante;

    Connection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate){
        this.id = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceCoordinate = new Coordinate(sourceCoordinate);
        this.destinationCoordiante = new Coordinate(destinationCoordinate);
    }


}
