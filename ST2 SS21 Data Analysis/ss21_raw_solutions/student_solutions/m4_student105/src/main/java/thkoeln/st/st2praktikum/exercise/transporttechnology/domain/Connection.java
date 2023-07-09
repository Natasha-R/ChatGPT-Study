package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.interfaces.Grid;
import thkoeln.st.st2praktikum.exercise.interfaces.Transporter;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Connection extends AbstractEntity implements Transporter {

    @ManyToOne
    private Deck source;

    @ManyToOne
    private Deck destination;

    @Embedded
    private Coordinate sourceCoordinate;

    @Embedded
    private Coordinate destinationCoordinate;

    public Connection(Deck src, Deck dest, Coordinate srcCoord, Coordinate destCoord) {
        this.source = src;
        this.destination = dest;
        this.sourceCoordinate = srcCoord;
        this.destinationCoordinate = destCoord;

        if (notInBounds(srcCoord, src)) throw new RuntimeException();
        if (notInBounds(destCoord, dest)) throw new RuntimeException();
    }

    private boolean notInBounds(Coordinate coord, Grid grid) {
        return coord.getX() < 0 || coord.getX() >= grid.getWidth() || coord.getY() < 0 || coord.getY() >= grid.getHeight();
    }

    public void setId(UUID id) {
        return;
    }
}

