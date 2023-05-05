package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.interfaces.Grid;
import thkoeln.st.st2praktikum.exercise.interfaces.Transporter;

@NoArgsConstructor
@Getter
public class Connection extends AbstractEntity implements Transporter {

    private Grid source;

    private Grid destination;

    private Coordinate sourceCoordinate;

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
}

