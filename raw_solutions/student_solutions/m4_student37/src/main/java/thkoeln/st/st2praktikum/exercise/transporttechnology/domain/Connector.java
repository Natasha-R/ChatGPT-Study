package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
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


    public Connector(Vector2D sourceVector, UUID destinationSpaceId, Vector2D destinationVector){
        connectorid=UUID.randomUUID();
        location1=sourceVector;
        location2=destinationVector;
        spacedestination=destinationSpaceId;
    }

    public Connector(String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate){
        connectorid=UUID.randomUUID();
        sourceCoordinate=sourceCoordinate.substring(1,sourceCoordinate.length()-1);
        destinationCoordinate=destinationCoordinate.substring(1,destinationCoordinate.length()-1);
        location1= Vector2D.fromString(sourceCoordinate);
        location2= Vector2D.fromString(destinationCoordinate);
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