package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.UUID;




@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class Connection  {
    private UUID sourceFieldid;
    private Coordinate sourceCoordinate;
    private  UUID destinationFieldid;
    private Coordinate destinationCoordinate;
    private UUID connectionUUID;
    private UUID transportCategoryId;


    public UUID getSourceFieldid() {
        return sourceFieldid;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationFieldid() {
        return destinationFieldid;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionUUID() {
        return connectionUUID;
    }

    public UUID getTransportCategoryId() {
        return transportCategoryId;
    }

    public Connection(UUID sourceFieldid, Coordinate sourceCoordinate, UUID destinationFieldid,
                      Coordinate destinationCoordinate, UUID connectionUUID, UUID transportCategoryId) {
        this.sourceFieldid = sourceFieldid;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldid = destinationFieldid;
        this.destinationCoordinate = destinationCoordinate;
        this.connectionUUID = connectionUUID;
        this.transportCategoryId = transportCategoryId;
    }


}
