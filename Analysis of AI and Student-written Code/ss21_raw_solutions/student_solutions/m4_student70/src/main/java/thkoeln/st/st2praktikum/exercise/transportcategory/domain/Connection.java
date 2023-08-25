package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Connection {
    @Id
    private UUID connectionId;
    private UUID sourceSpaceId;
    private UUID destinationSpaceId;
    Point sourceCoordinate, destinationCoordinate;

    public Connection(UUID sourceSpaceId, Point sourceCoordinate, UUID destinationSpaceId, Point destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;
        this.sourceCoordinate =  sourceCoordinate;
        this.destinationCoordinate =  destinationCoordinate;


    }

}
