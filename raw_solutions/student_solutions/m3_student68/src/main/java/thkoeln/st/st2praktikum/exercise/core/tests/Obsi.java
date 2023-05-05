package thkoeln.st.st2praktikum.exercise.core.tests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.Direction;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor
@Getter
public class Obsi implements Block {

    @Embedded
    private Coor start;

    @Embedded
    private Coor end;


    public Obsi(Coor pos1, Coor pos2) {
        this.start = pos1;
        this.end = pos1;
    }

     /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Obsi(String barrierString) {
        String[] separatedCoordinates = barrierString.split("-");
        this.start = new Coor(separatedCoordinates[0]);
    }


    public Coor getBlockCoordinate(Coor currentCoordinate, Coor targetCoordinate) {
        Direction blockDirection = Direction.EAST;
        switch (blockDirection) {
            case NORTH:  return currentCoordinate;
            case SOUTH:  return currentCoordinate;
            case WEST:   return currentCoordinate;
            case EAST:   return currentCoordinate;
         default: throw new IllegalArgumentException("Illegal BlockDirection: "+ blockDirection);
         }
    }
}