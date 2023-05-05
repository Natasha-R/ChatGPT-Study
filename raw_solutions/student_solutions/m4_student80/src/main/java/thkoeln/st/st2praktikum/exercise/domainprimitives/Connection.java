package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Connection {
    private UUID sourceRoomID;
    private Coordinate sourceCoordinate;
    private UUID destinationRoomId;
    private Coordinate destinationCoordinate;
    private UUID connectionUUID;
    private UUID transportCategoryId;




    public Connection(UUID sourceRoomID, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate, UUID connectionUUID, UUID transportCategoryId) {
        this.sourceRoomID = sourceRoomID;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.connectionUUID = connectionUUID;
        this.transportCategoryId = transportCategoryId;

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

    public UUID getTransportCategoryId(){
        return transportCategoryId;
    }
    public UUID getSourceRoomID() {
        return sourceRoomID;
    }


}
