package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;


import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class Connection {

    @Id
    private final UUID connectionId = UUID.randomUUID();

    private UUID transportTechnologyId;

    private UUID sourceSpaceId;

    @Embedded
    private Coordinate sourceCoordinate;

    private UUID destinationSpaceId;

    @Embedded
    private Coordinate destinationCoordinate;


    public Connection(UUID transportTechnologyId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        this.transportTechnologyId = transportTechnologyId;
        this.sourceSpaceId = sourceSpaceId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceId = destinationSpaceId;
        this.destinationCoordinate = destinationCoordinate;
    }

    protected Connection() {
    }
}
