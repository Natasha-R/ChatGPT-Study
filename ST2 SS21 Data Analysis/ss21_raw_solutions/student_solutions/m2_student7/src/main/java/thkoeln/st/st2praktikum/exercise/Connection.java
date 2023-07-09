package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private Room sourceRoom;
    private Room destinationRoom;
    private Vector2D sourceVector2D;
    private Vector2D destinationVector2D;
    private UUID id = UUID.randomUUID();

    public Connection(Room sourceRoom, Vector2D sourceVector2D, Room destinationRoom, Vector2D destinationVector2D){
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;

        this.sourceVector2D = sourceVector2D;
        this.destinationVector2D = destinationVector2D;
    }

    public UUID getId(){
        return id;
    }

    public Vector2D getSourceCoordinates() {
        return sourceVector2D;
    }
    public Vector2D getDestinationCoordinates() {
        return destinationVector2D;
    }
    public Room getSourceRoom() {
        return sourceRoom;
    }
    public Room getDestinationRoom() {
        return destinationRoom;
    }
}
