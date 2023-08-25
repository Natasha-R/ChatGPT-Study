package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Getter
@Setter
public class Barrier {

    private UUID id=UUID.randomUUID();
    private UUID idSpaceShip;
    private char barrierType;
    private Point start;
    private Point end;

    public Barrier(){}
    public Barrier(Point start, Point end) {
        if (checkBarrierFormat(start, end)) {
            if (start.getX() < end.getX()) {
                this.start = start;
                this.end = end;
            } else if (start.getX() > end.getX()) {
                this.start = end;
                this.end = start;
            } else {
                if (start.getY() < end.getY()) {
                    this.start = start;
                    this.end = end;
                } else {
                    this.start = end;
                    this.end = start;
                }
            }
        } else
            throw new RuntimeException("choose a valid number");
    }
    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        Barrier barrier=new Barrier();
        String[] divideString = barrierString.split("-");
        barrier.setStart( Point.fromString( divideString[0] )  );
        barrier.setEnd( Point.fromString( divideString[1] ));
        return barrier;
    }
    public Boolean checkBarrierFormat(Point p1, Point p2) {
        if (p1.getX().equals(p2.getX()))
            return !p1.getY().equals(p2.getY());
        if (p1.getY().equals(p2.getY()))
            return !p1.getX().equals(p2.getX());
        return false;
    }
}
