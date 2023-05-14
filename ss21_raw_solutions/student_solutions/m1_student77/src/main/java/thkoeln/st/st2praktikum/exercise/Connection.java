package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID id = UUID.randomUUID();
    private UUID sourceId;
    private UUID destinationId;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceRoomCoordinate,UUID destinationRoomId, String destinationRoomCoordinate) {
        this.sourceId = sourceRoomId;
        this.sourceCoordinate = Coordinate.getCoordinate(sourceRoomCoordinate);
        this.destinationId = destinationRoomId;
        this.destinationCoordinate = Coordinate.getCoordinate(destinationRoomCoordinate);
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getSourceId() {
        return this.sourceId;
    }

    public UUID getDestinationId() {
        return this.destinationId;
    }

    public Coordinate getSourceCoordinate() {
        return this.sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

}
