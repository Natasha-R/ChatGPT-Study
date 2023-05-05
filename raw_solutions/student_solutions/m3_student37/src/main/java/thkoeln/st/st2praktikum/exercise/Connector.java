package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor

public class Connector {
    @Id
    private UUID spacedestination;
    private UUID connectorid;
    @Embedded
    private Vector2D location1;
    @Embedded
    private Vector2D location2;


    public Connector( Vector2D sourceVector, UUID destinationSpaceId, Vector2D destinationVector){
        connectorid=UUID.randomUUID();
        location1=sourceVector;
        location2=destinationVector;
        spacedestination=destinationSpaceId;
    }

    public Vector2D getLocation1() {
        return location1;
    }

    public UUID getSpacedestination() {
        return spacedestination;
    }

    public Vector2D getLocation2() {
        return location2;
    }

    public UUID getId(){
        return connectorid;
    }
}