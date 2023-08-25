package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

//UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate
public class Connection {
    private UUID sourceRoomID;
    private Coordinate sourceCoordinate;
    private UUID destinationRoomId;
    private Coordinate destinationCoordinate;
    private UUID connectionUUID;


    public UUID getSourceRoomID() {
        return sourceRoomID;
    }

    public Connection(UUID sourceRoomID, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate, UUID connectionUUID) {
        this.sourceRoomID = sourceRoomID;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.connectionUUID = connectionUUID;

    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationRoomId() {
        return destinationRoomId;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionUUID() {
        return connectionUUID;
    }




}
