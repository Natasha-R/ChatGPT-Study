package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.Direction;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocking;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor
@Getter
public class Obstacle implements Blocking {

    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;

    public Obstacle(Barrier barrier) {
        this.start = barrier.getStart();
        this.end = barrier.getEnd();
    }

    public Obstacle(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
    }

     /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String barrierString) {
        String[] separatedCoordinates = barrierString.split("-");
        this.start = new Coordinate(separatedCoordinates[0]);
        this.end = new Coordinate(separatedCoordinates[1]);
    }


    public Coordinate getBlockCoordinate(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Direction blockDirection = calcDirection(currentCoordinate, targetCoordinate);
        switch (blockDirection) {
            case NORTH:  return calcBlockCoordinateNorth(currentCoordinate, targetCoordinate);
            case SOUTH:  return calcBlockCoordinateSouth(currentCoordinate, targetCoordinate);
            case WEST:   return calcBlockCoordinateWest(currentCoordinate, targetCoordinate);
            case EAST:   return calcBlockCoordinateEast(currentCoordinate, targetCoordinate);
         default: throw new IllegalArgumentException("Illegal BlockDirection: "+ blockDirection);
         }
    }

    private static Direction calcDirection(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Integer horizontalSteps = targetCoordinate.getX() - currentCoordinate.getX();
        Integer verticalSteps = targetCoordinate.getY() - currentCoordinate.getY();
        if (verticalSteps > 0)          return Direction.NORTH;
        else if (verticalSteps < 0)     return Direction.SOUTH;
        else if (horizontalSteps > 0)   return Direction.EAST;
        else if (horizontalSteps < 0)   return Direction.WEST;
        else                            return Direction.NONE;
    }

    private Coordinate calcBlockCoordinateNorth(Coordinate currentCoordinate, Coordinate targetCoordinate) {              // TODO - Kommentare für Fehlerkorrektur
        if (getYStart() == getYEnd()                                                                                      // Wenn horizontale Barriere
                    && getYStart() > currentCoordinate.getY()                                                             // & Barriere liegt im nördlichen Bereich
                    && getXStart() <= currentCoordinate.getX()                                                            // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getXEnd() > currentCoordinate.getX()                                                               // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getYStart() <= targetCoordinate.getY())                                                            // & Barriere Schränkt Bewegung ein
                return new Coordinate(targetCoordinate.getX(), getYStart()-1);                                         // => Neue BlockPosition wird zurückgegeben                                                             // => Setzt neuen StopPoint
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockCoordinateSouth(Coordinate currentCoordinate, Coordinate targetCoordinate) {              // TODO - Kommentare für Fehlerkorrektur
        if (getYStart() == getYEnd()                                                                                      // Wenn horizontale Barriere
                    && getYStart() <= currentCoordinate.getY()                                                            // & Barriere liegt im südlichen Bereich
                    && getXStart() <= currentCoordinate.getX()                                                            // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getXEnd() > currentCoordinate.getX()                                                               // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getYStart() > targetCoordinate.getY())                                                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                return new Coordinate(targetCoordinate.getX(), getYStart());                                              // => Neue BlockPosition wird zurückgegeben
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockCoordinateEast(Coordinate currentCoordinate, Coordinate targetCoordinate) {               // TODO - Kommentare für Fehlerkorrektur
        if (getXStart() == getXEnd()                                                                                      // Wenn vertikale Barriere
                    && getXStart() > currentCoordinate.getX()                                                             // & Barriere liegt im östlichen Bereich
                    && getYStart() <= currentCoordinate.getY()                                                            // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getYEnd() > currentCoordinate.getY()                                                               // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getXStart() <= targetCoordinate.getX())                                                            // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                return new Coordinate(getXStart()-1, targetCoordinate.getY());                                         // => Neue BlockPosition wird zurückgegeben
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockCoordinateWest(Coordinate currentCoordinate, Coordinate targetCoordinate) {               // TODO - Kommentare für Fehlerkorrektur
        if (getXStart() == getXEnd()                                                                                      // Wenn vertikale Barriere
                    && getXStart() <= currentCoordinate.getX()                                                            // & Barriere liegt im östlichen Bereich
                    && getYStart() <= currentCoordinate.getY()                                                            // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getYEnd() > currentCoordinate.getY()                                                               // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getXStart() > targetCoordinate.getX())                                                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                return new Coordinate(getXStart(), targetCoordinate.getY());                                              // => Neue BlockPosition wird zurückgegeben
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

     private Integer getXStart() { return this.start.getX(); }

     private Integer getYStart() {
        return this.start.getY();
    }

     private Integer getXEnd() { return this.end.getX(); }

     private Integer getYEnd() {
        return this.end.getY();
    }
}

