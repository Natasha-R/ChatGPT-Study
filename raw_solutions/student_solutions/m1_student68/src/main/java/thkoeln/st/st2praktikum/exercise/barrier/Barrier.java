package thkoeln.st.st2praktikum.exercise.barrier;
import thkoeln.st.st2praktikum.exercise.core.Direction;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;


public class Barrier implements Blockable {
    private Coordinate start;
    private Coordinate end;

    public Barrier(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
    }

     /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier (String barrierString) {
        String[] separatedCoordinates = barrierString.split("-");
        this.start = new Coordinate(separatedCoordinates[0]);
        this.end = new Coordinate(separatedCoordinates[1]);
    }

    public Coordinate getBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate) {
        Direction blockDirection = calcDirection(currentCoordinate, targetCoordinate);
        switch (blockDirection) {
            case NORTH:  return calcBlockPositionNorth(currentCoordinate, targetCoordinate);
            case SOUTH:  return calcBlockPositionSouth(currentCoordinate, targetCoordinate);
            case WEST:   return calcBlockPositionWest(currentCoordinate, targetCoordinate);
            case EAST:   return calcBlockPositionEast(currentCoordinate, targetCoordinate);
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

    private Coordinate calcBlockPositionNorth(Coordinate currentCoordinate, Coordinate targetCoordinate) {                // TODO - Kommentare für Fehlerkorrektur
        if (getYStart() == getYEnd()                                                                                      // Wenn horizontale Barriere
                    && getYStart() > currentCoordinate.getY()                                                             // & Barriere liegt im nördlichen Bereich
                    && getXStart() <= currentCoordinate.getX()                                                            // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getXEnd() > currentCoordinate.getX()                                                               // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getYStart() <= targetCoordinate.getY())                                                            // & Barriere Schränkt Bewegung ein
                return new Coordinate(targetCoordinate.getX(), getYStart()-1);                                         // => Neue BlockPosition wird zurückgegeben                                                             // => Setzt neuen StopPoint
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockPositionSouth(Coordinate currentCoordinate, Coordinate targetCoordinate) {                // TODO - Kommentare für Fehlerkorrektur
        if (getYStart() == getYEnd()                                                                                      // Wenn horizontale Barriere
                    && getYStart() <= currentCoordinate.getY()                                                            // & Barriere liegt im südlichen Bereich
                    && getXStart() <= currentCoordinate.getX()                                                            // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getXEnd() > currentCoordinate.getX()                                                               // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                    && getYStart() > targetCoordinate.getY())                                                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                return new Coordinate(targetCoordinate.getX(), getYStart());                                              // => Neue BlockPosition wird zurückgegeben
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockPositionEast(Coordinate currentCoordinate, Coordinate targetCoordinate) {                 // TODO - Kommentare für Fehlerkorrektur
        if (getXStart() == getXEnd()                                                                                      // Wenn vertikale Barriere
                    && getXStart() > currentCoordinate.getX()                                                             // & Barriere liegt im östlichen Bereich
                    && getYStart() <= currentCoordinate.getY()                                                            // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getYEnd() > currentCoordinate.getY()                                                               // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                    && getXStart() <= targetCoordinate.getX())                                                            // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                return new Coordinate(getXStart()-1, targetCoordinate.getY());                                         // => Neue BlockPosition wird zurückgegeben
        return targetCoordinate;                                                                                          // Ansonsten ist Blockposition = targetPosition
    }

    private Coordinate calcBlockPositionWest(Coordinate currentCoordinate, Coordinate targetCoordinate) {                 // TODO - Kommentare für Fehlerkorrektur
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

