package thkoeln.st.st2praktikum.exercise.transportcategory.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.UUID;


@Entity
@AllArgsConstructor
public class Connection {
    @Id
    @Getter
    private UUID connectionID;
    private UUID sourceRoomID;
    private UUID destinationRoomID;

    @Embedded
    private Coordinate sourceCoordinates;
    @Embedded
    private Coordinate destinationCoordinates;

    public Connection() {this.connectionID = UUID.randomUUID();}

    public UUID getConnectionID(){return connectionID;}

    public void setSourceRoomID ( UUID sourceRoomID){
        this.sourceRoomID = sourceRoomID;
    }

    public UUID getSourceRoomID() {
        return sourceRoomID;
    }

    public void setDestinationRoomID ( UUID destinationRoomID){
        this.destinationRoomID = destinationRoomID;
    }

    public UUID getDestinationRoomID() {
        return destinationRoomID;
    }

    public void setSourceCoordinates ( Coordinate sourceCoordinates){
        this.sourceCoordinates = sourceCoordinates;
    }

    public Coordinate getSourceCoordinates() {
        return sourceCoordinates;
    }

    public void setDestinationCoordinates ( Coordinate destinationCoordinates){
        this.destinationCoordinates = destinationCoordinates;
    }

    public Coordinate getDestinationCoordinates() {
        return destinationCoordinates;
    }
}
