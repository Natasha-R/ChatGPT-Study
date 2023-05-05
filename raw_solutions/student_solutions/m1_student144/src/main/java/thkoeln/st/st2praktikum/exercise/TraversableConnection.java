package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
public class TraversableConnection {
    protected UUID sourceRoomId;
    protected String sourceCoordinate;
    protected UUID destinationRoomId;
    protected String destinationCoordinate;
    protected UUID connectionId;

    public TraversableConnection(UUID willBeSourceRoomId, String willBSourceCoordinate, UUID willBeDestinationRoomId, String willBeDestinationCoordinate){
      sourceRoomId = willBeSourceRoomId;
      sourceCoordinate = willBSourceCoordinate;
      destinationRoomId = willBeDestinationRoomId;
      destinationCoordinate = willBeDestinationCoordinate;
      connectionId = UUID.randomUUID();
    }
}
