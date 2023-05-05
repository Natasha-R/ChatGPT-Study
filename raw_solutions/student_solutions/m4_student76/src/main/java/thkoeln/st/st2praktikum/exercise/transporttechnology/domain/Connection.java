package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Connection extends AbstractEntity {


    private UUID sourceSpaceShipDeck;
    private UUID destinationSpaceShipDeck;

    @Embedded
    private Point sourcePoint;
    @Embedded
    private Point destinationPoint;

    public Connection(UUID sourceSpaceShipDeck, UUID destinationSpaceShipDeck, Point sourcePoint, Point destinationPoint){
        this.sourceSpaceShipDeck = sourceSpaceShipDeck;
        this.destinationSpaceShipDeck = destinationSpaceShipDeck;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }

}

