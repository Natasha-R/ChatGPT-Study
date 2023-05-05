package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    protected UUID id;
    protected UUID destinationRoomId;
    protected Coordinate sourceCoordinate;
    protected Coordinate destinationCoordinate;

    public Connection (String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        this.id = UUID.randomUUID();
        this.destinationRoomId = destinationRoomId;
        this.sourceCoordinate = new Coordinate(sourceCoordinate);
        this.destinationCoordinate = new Coordinate(destinationCoordinate);
    }
}