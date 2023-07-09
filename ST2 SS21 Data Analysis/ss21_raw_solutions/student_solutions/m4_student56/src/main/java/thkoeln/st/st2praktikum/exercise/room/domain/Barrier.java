package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.*;
import java.util.UUID;



public class Barrier {




    private Coordinate start;
    private Coordinate end;


    public Barrier(Coordinate start, Coordinate end) {

        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())){
            throw new RuntimeException("No diagonale barriers allowed.");
        }
        if (start.getX() + start.getY() > end.getX() + end.getY()){
            this.start = end;
            this.end = start;
        }else {
            this.start = start;
            this.end = end;
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        if (barrierString.length() != 11){
            throw new RuntimeException("Barrierstring has not the requierd length.");
        }
        if (barrierString.charAt(5) != '-'){
            throw new RuntimeException("No valid Barrierstring.");
        }


        Coordinate start = Coordinate.fromString(barrierString.substring(0,5));
        Coordinate end   = Coordinate.fromString(barrierString.substring(6,11));

        Barrier barrier = new Barrier(Coordinate.fromString(barrierString.substring(0,5)),Coordinate.fromString(barrierString.substring(6,11)) );
        String string = barrierString;

        return barrier;


    }

}
