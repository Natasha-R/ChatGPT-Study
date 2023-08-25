package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Connection {
    @Id
    private  UUID connectionId;

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
