package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
public class TraversableConnection {
    private UUID sourceRoomId;
    private Point sourcePoint;
    private UUID destinationRoomId;
    private Point destinationPoint;
    private UUID connectionId;

    public TraversableConnection(UUID willBeSourceRoomId, Point willBSourcePoint, UUID willBeDestinationRoomId, Point willBeDestinationPoint){
      sourceRoomId = willBeSourceRoomId;
      sourcePoint = willBSourcePoint;
      destinationRoomId = willBeDestinationRoomId;
      destinationPoint = willBeDestinationPoint;
      connectionId = UUID.randomUUID();
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

    public UUID getDestinationRoomId() {
        return destinationRoomId;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public UUID getSourceRoomId() {
        return sourceRoomId;
    }
}
