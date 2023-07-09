package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
@Getter
@AllArgsConstructor
@Setter
@Entity
@NoArgsConstructor
public class TraversableConnection {
    private UUID sourceRoomId;
    private Point sourcePoint;
    private UUID destinationRoomId;

    @Embedded
    private Point destinationPoint;

    @Id
    private UUID connectionId;

    private UUID transportTechnology;

    public TraversableConnection(UUID willBeTransportTechnology, UUID willBeSourceRoomId, Point willBSourcePoint, UUID willBeDestinationRoomId, Point willBeDestinationPoint){
      sourceRoomId = willBeSourceRoomId;
      sourcePoint = willBSourcePoint;
      destinationRoomId = willBeDestinationRoomId;
      destinationPoint = willBeDestinationPoint;
      connectionId = UUID.randomUUID();
      transportTechnology = willBeTransportTechnology;
    }

    public UUID getTransportTechnology(){
        return this.transportTechnology;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public UUID getSourceRoomId() {
        return sourceRoomId;
    }
}
