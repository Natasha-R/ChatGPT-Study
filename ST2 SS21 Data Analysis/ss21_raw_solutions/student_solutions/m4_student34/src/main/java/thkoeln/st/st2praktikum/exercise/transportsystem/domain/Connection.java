package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Connection extends AbstractEntity {

    @ManyToOne
    private SpaceShipDeck sourceSpaceShip;
    @Embedded
    private Coordinate sourceCoordinate;
    @ManyToOne
    private SpaceShipDeck destinationSpaceShipDeck;
    @Embedded
    private Coordinate destinationCoordinate;
}
