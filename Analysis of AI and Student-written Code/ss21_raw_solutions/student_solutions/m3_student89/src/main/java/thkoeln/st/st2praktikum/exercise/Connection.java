package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.UUID;
@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity  {
    @ManyToOne
    private TransportCategory transport_category;
    private UUID entranceRoomID;
    @Transient
    private Vector2D entranceCoordinates;
    private UUID exitRoomID;
    @Transient
    private Vector2D exitCoordinates;
    public Connection(TransportCategory transport_category,UUID entranceRoomID, Vector2D entranceCoordinates, UUID exitRoomID, Vector2D exitCoordinates){

        this.transport_category=transport_category;
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

    public TransportCategory getTransport_category(){return transport_category;}
}

