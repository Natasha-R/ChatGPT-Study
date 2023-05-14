package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity {
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_1")),
            @AttributeOverride(name = "y", column = @Column(name = "y_1"))
    })
    @Embedded
    private Vector2D entranceCoordinates;
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_2")),
            @AttributeOverride(name = "y", column = @Column(name = "y_2"))
    })
    @Embedded
    private Vector2D exitCoordinates;
    private UUID entranceRoomID;
    private UUID exitRoomID;
    public Connection(UUID entranceRoomID, Vector2D entranceCoordinates, UUID exitRoomID, Vector2D exitCoordinates){

        this.entranceRoomID=entranceRoomID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitRoomID=exitRoomID;
        this.exitCoordinates=exitCoordinates;


    }




    public Vector2D getEntranceCoordinates() {
        return entranceCoordinates;
    }

    public Vector2D getExitCoordinates() {
        return exitCoordinates;
    }

    public UUID getEntranceRoomID() {
        return entranceRoomID;
    }

    public UUID getExitRoomID() {
        return exitRoomID;
    }


}

