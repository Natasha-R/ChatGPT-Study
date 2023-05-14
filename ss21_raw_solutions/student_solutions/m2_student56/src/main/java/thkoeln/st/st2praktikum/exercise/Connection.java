package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID connectionID;
    private UUID sourceRoomID;
    private UUID destinationRoomID;
    private Coordinate sourceCoordinates;
    private Coordinate destinationCoordinates;


    public void setConnectionID ( UUID connectionID){
        this.connectionID = connectionID;
    }

    public UUID getConnectionID() {
        return connectionID;
    }

    public void setSourceRoomID ( UUID sourceRoomID){
        this.sourceRoomID = sourceRoomID;
    }

    public UUID getSourceRoomID() {
        return sourceRoomID;
    }

    public void setDestinationRoomID ( UUID destinationRoomID){
        this.destinationRoomID = destinationRoomID;
    }

    public UUID getDestinationRoomID() {
        return destinationRoomID;
    }

    public void setSourceCoordinates ( Coordinate sourceCoordinates){
        this.sourceCoordinates = sourceCoordinates;
    }

    public Coordinate getSourceCoordinates() {
        return sourceCoordinates;
    }

    public void setDestinationCoordinates ( Coordinate destinationCoordinates){
        this.destinationCoordinates = destinationCoordinates;
    }

    public Coordinate getDestinationCoordinates() {
        return destinationCoordinates;
    }
}
