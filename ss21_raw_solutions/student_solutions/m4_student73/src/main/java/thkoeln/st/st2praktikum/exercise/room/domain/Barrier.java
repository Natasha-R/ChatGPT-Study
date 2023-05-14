package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidBarrierException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidStringException;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Barrier {

    private Vector2D start;
    private Vector2D end;


    public Barrier(Vector2D pos1, Vector2D pos2) {
        if((pos1.getX() < pos2.getX() && pos1.getY() == pos2.getY()) || (pos1.getX() == pos2.getX() && pos1.getY() < pos2.getY())) {
            this.start = pos1;
            this.end = pos2;
        } else if ((pos1.getX() > pos2.getX() && pos1.getY() == pos2.getY()) || (pos1.getX() == pos2.getX() && pos1.getY() > pos2.getY())) {
            this.start = pos2;
            this.end = pos1;
        } else throw new InvalidBarrierException("Please check your Vectors");
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        String pos1;
        String pos2;
        if (barrierString.contains("-")) {
            String[] parts = barrierString.split("-");
            pos1 = parts[0];
            pos2 = parts[1];
        } else throw new InvalidStringException("Please input a correct String");
        if(CheckStringSyntax(pos1, pos2)) {
            this.start = new Vector2D(pos1);
            this.end = new Vector2D(pos2);
        } else throw new InvalidStringException("Please input a correct String");
    }

    public static Barrier fromString(String s) {
        return new Barrier(s);
    }

    private boolean CheckStringSyntax(String pos1, String pos2) {
        try {
            Vector2D firstVector = new Vector2D(pos1);
            Vector2D secondVector = new Vector2D(pos2);
            return firstVector.getX() <= secondVector.getX() && firstVector.getY() <= secondVector.getY();
        } catch (Exception e) {
            return false;
        }
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }
}
