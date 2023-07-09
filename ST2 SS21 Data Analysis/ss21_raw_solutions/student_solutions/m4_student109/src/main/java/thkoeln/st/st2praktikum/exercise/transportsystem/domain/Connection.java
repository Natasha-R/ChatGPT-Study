package thkoeln.st.st2praktikum.exercise.transportsystem.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Connection {

    @Id
    private UUID connectionId;
    private UUID spaceShipDeckId;
    private UUID destinationSpaceShipDeckId;

    @Embedded
    private Vector2D sourceCoordinate;
    @Embedded
    private Vector2D destinationCoordinate;

    private  UUID TransportSystemId;


    public Connection(UUID transportSystemId, UUID spaceShipDeckId, Vector2D sourceCoordinate, UUID destinationSpaceShipDeckId, Vector2D destinationCoordinate, UUID connectionId) {
        this.TransportSystemId = transportSystemId;
        this.connectionId = connectionId;
        this.spaceShipDeckId = spaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate= destinationCoordinate;

    }




}
