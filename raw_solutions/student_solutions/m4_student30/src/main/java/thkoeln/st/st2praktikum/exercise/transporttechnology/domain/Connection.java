package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Connection extends AbstractEntity {

    private UUID entranceShipDeckID;
    private UUID exitShipDeckID;

    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_1")),
            @AttributeOverride(name = "y", column = @Column(name = "y_1"))
    })
    @Embedded
    private Coordinate entranceCoordinates;


    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "x_2")),
            @AttributeOverride(name = "y", column = @Column(name = "y_2"))
    })
    @Embedded
    private Coordinate exitCoordinates;

    public Connection(UUID entranceShipDeckID, Coordinate entranceCoordinates,UUID exitShipDeckID, Coordinate exitCoordinates){
        this.entranceShipDeckID=entranceShipDeckID;
        this.entranceCoordinates=entranceCoordinates;
        this.exitShipDeckID=exitShipDeckID;
        this.exitCoordinates=exitCoordinates;
    }

}
