package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection {

    @Id
    private UUID connectionId;
    private UUID sourceFieldId;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destinationCoordinate;
    private UUID transportTechnologyId;

    public Connection(UUID connectionId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate, UUID transportTechnologyId) {
        this.connectionId = connectionId;
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
        this.transportTechnologyId = transportTechnologyId;
    }
}
