package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Connection {
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private TransportCategory transportCategory;
    @OneToOne
    private Room sourceRoom;
    @OneToOne
    private Room destinationRoom;
    @Embedded
    private Vector2D sourceCoordinates;
    @Embedded
    private Vector2D destinationCoordinates;


    public Connection(TransportCategory transportCategory, Room sourceRoom, Vector2D sourceVector2D, Room destinationRoom, Vector2D destinationVector2D){
        this.transportCategory = transportCategory;

        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;

        this.sourceCoordinates = sourceVector2D;
        this.destinationCoordinates = destinationVector2D;
    }


}