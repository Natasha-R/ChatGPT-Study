package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;


@Getter
@Setter
public class Barrier {

    private UUID idSpaceShip;
    private char barrierType; //ENUM
    private Point start;
    private Point end;

    public Barrier() {
    }

    public Barrier(Point pos1, Point pos2) {
        if (checkBarrierFormat(pos1, pos2)) {
            if (pos1.getX() < pos2.getX()) {
                this.start = pos1;
                this.end = pos2;
            } else if (pos1.getX() > pos2.getX()) {
                this.start = pos2;
                this.end = pos1;
            } else {
                if (pos1.getY() < pos2.getY()) {
                    this.start = pos1;
                    this.end = pos2;
                } else {
                    this.start = pos2;
                    this.end = pos1;
                }
            }
        } else
            throw new RuntimeException("choose a valid number");
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        String[] divideString = barrierString.split("-");
        this.start = new Point(divideString[0]);
        this.end = new Point(divideString[1]);
    }
    public Boolean checkBarrierFormat(Point p1, Point p2) {//Oben
        if (p1.getX().equals(p2.getX()))
            return !p1.getY().equals(p2.getY());
        if (p1.getY().equals(p2.getY()))
            return !p1.getX().equals(p2.getX());
        return false;
    }
}
