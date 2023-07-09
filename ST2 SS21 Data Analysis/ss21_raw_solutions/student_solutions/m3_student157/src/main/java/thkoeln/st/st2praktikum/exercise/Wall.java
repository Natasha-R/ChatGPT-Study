package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class Wall {

    @Embedded
    private Vector2D start;

    @Embedded
    private Vector2D end;

    public Boolean isBlocking(Vector2D currentPoint, Vector2D nextPoint) {
        if (currentPoint.isGreaterThan(nextPoint)) {
            Vector2D temp = currentPoint;
            currentPoint = nextPoint;
            nextPoint = temp;
        }

        if ( fetchAxis().equals("horizontal") && isCrossingThePlane(start.getY(), currentPoint.getY(), nextPoint.getY()) ) {
            return nextPoint.getX() >= start.getX() && nextPoint.getX() < end.getX();
        } else if ( fetchAxis().equals("vertical") && isCrossingThePlane(start.getX(), currentPoint.getX(), nextPoint.getX()) ) {
            return nextPoint.getY() >= start.getY() && nextPoint.getY() < end.getY();
        }

        return false;
    }

    private String fetchAxis() {
        if (start.getY().equals(end.getY())) {
            return "horizontal";
        } else if (start.getX().equals(end.getX())) {
            return "vertical";
        } else {
            throw new RuntimeException("The barrier " + this + " is neither vertical nor horizontal.");
        }
    }

    private Boolean isCrossingThePlane(int plane, int current, int next) {
        return current < plane && next == plane;
    }

    private void checkIfBarrierStringIsValid(String barrierString) {
        Pattern pattern = Pattern.compile("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$");
        Matcher matcher = pattern.matcher(barrierString);
        if (!matcher.matches()) {
            throw new RuntimeException("\"" + barrierString + "\" is not a valid barrierString in Barrier().");
        }
    }

    @Override
    public String toString() {
        return  start.toString() + "-" + end.toString();
    }

    public Vector2D getStart() {
        return start;
    }
    public Vector2D getEnd() {
        return end;
    }

    public Wall(String barrierString) {
        checkIfBarrierStringIsValid(barrierString);

        final StringTokenizer st = new StringTokenizer(barrierString, "-");
        final Vector2D _start = new Vector2D(st.nextToken());
        final Vector2D _end = new Vector2D(st.nextToken());

        if (_start.isGreaterThan(_end)) {
            this.start = _end;
            this.end = _start;
        } else {
            this.start = _start;
            this.end = _end;
        }
        fetchAxis(); // Throws an exception if the the barrier is neither vertical nor horizontal.
    }
    public Wall(Vector2D pos1, Vector2D pos2) {
        if (pos1.isGreaterThan(pos2)) {
            this.start = pos2;
            this.end = pos1;
        } else {
            this.start = pos1;
            this.end = pos2;
        }
        fetchAxis(); // Throws an exception if the the barrier is neither vertical nor horizontal.
    }
}
