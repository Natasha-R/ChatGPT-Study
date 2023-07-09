package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;
import thkoeln.st.st2praktikum.exercise.interfaces.Transporter;
import thkoeln.st.st2praktikum.exercise.interfaces.Grid;

@NoArgsConstructor
@Getter
public class Connection extends AbstractEntity implements Transporter {

    Grid source;

    Grid destination;

    Coordinate sourceCoordinate;

    Coordinate destinationCoordinate;

    public Connection(Deck src, Deck dest, String srcCoord, String destCoord) {
        this.source = src;
        this.destination = dest;
        try {
            this.sourceCoordinate = Coordinate.fromString(srcCoord);
            this.destinationCoordinate = Coordinate.fromString(destCoord);
        } catch (ParseException pe) {
            System.out.println("Could not parse connection coordinates");
        }

    }
}
