package thkoeln.st.st2praktikum.exercise.transportcategory.domain;


import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Connection {
    @Id
    private UUID connectionId;

    @Embedded
    private Coordinate sourcePosition;

    @Embedded
    private  Coordinate destinationPosition;

    @ManyToOne
    private TransportCategory category;

    private UUID sourceSpaceId;
    private UUID destinationSpaceID;

    public Connection(TransportCategory category,UUID sourceSpaceId,Coordinate sourcePosition, UUID destinationSpaceID, Coordinate destinationPosition){
        this.category=category;

        this.connectionId=UUID.randomUUID();
        this.sourceSpaceId=sourceSpaceId;
        this.sourcePosition=sourcePosition;
        this.destinationSpaceID=destinationSpaceID;
        this.destinationPosition=destinationPosition;
    }

    public Connection() { }
}
