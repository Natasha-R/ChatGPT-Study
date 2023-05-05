package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity {


    private UUID sourceRoom;
    private UUID destinationRoom;

    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceRoom, UUID destinationRoom, Coordinate sourceCoordinate, Coordinate destinationCoordinate){
        this.sourceRoom = sourceRoom;
        this.destinationRoom = destinationRoom;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

}