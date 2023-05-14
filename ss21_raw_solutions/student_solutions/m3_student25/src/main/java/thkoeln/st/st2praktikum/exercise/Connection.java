package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class Connection {
    @Id
    private final UUID connectionID = UUID.randomUUID();
    private UUID sourceSpaceShipDeckId;
    @Embedded
    private Coordinate sourceCoordinate;
    private UUID destinationSpaceShipDeckId;
    @Embedded
    private Coordinate destinationCoordinate;
    private UUID transportCategoryID;

    protected Connection() {
    }

    public Connection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate, UUID transportCategoryID) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationCoordinate;
        this.transportCategoryID = transportCategoryID;
    }
}
