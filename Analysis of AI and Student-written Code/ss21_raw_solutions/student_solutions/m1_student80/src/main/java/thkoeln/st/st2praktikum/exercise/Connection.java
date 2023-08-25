package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.UUID;

//UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate
public class Connection {
    private UUID sourceRoomID;
    private String sourceCoordinate;
    private UUID destinationRoomId;
    private String destinationCoordinate;
    private UUID connectionUUID;
    private int[] startCoordinates;
    private int[] endCoordinates;

    public UUID getSourceRoomID() {
        return sourceRoomID;
    }


    public int[] startCoordinates(String startCoordinates) {
        startCoordinates = startCoordinates.replaceAll("[()]", "");
        return Arrays.stream(startCoordinates.split(", *")).mapToInt(Integer::parseInt).toArray();
    }

    public int[] endCoordinates(String destinationCoordinate) {
        destinationCoordinate = destinationCoordinate.replaceAll("[()]", "");
        return Arrays.stream(destinationCoordinate.split(", *")).mapToInt(Integer::parseInt).toArray();
    }


    public Connection(UUID sourceRoomID, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate, UUID connectionUUID) {
        this.sourceRoomID = sourceRoomID;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.connectionUUID = connectionUUID;
        this.startCoordinates = startCoordinates(sourceCoordinate);
        this.endCoordinates = endCoordinates(destinationCoordinate);
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationRoomId() {
        return destinationRoomId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionUUID() {
        return connectionUUID;
    }

    public int[] getStartCoordinates() {
        return startCoordinates;
    }

    public int[] getEndCoordinates() {
        return endCoordinates;
    }


}
