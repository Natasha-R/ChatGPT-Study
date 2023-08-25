package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class Connection {

    private UUID id;
    private UUID destinationRoomId;
    private Point sourcePoint;
    private Point destinationPoint;


    public Connection (Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        this.id = UUID.randomUUID();
        this.destinationRoomId = destinationRoomId;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }


    public UUID getId () {
        return this.id;
    }


    public UUID getDestinationRoomId () {
        return this.destinationRoomId;
    }


    public Point getSourcePoint () {
        return this.sourcePoint;
    }


    public Point getDestinationPoint () {
        return this.destinationPoint;
    }
}   