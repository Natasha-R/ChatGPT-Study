package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Connection extends AbstractEntity {

    @Getter
    private UUID transportSystemId;

    @Getter
    @Embedded
    private Vector2D sourceRoomCoordinates;

    @Getter
    @Embedded
    private Vector2D destinationRoomCoordinates;

    public Connection(UUID transportSystemId, Vector2D sourceRoomCoordinates, Vector2D destinationRoomCoordinates){
        this.transportSystemId = transportSystemId;
        this.sourceRoomCoordinates = sourceRoomCoordinates;
        this.destinationRoomCoordinates = destinationRoomCoordinates;
    }

    protected Connection(){}
}