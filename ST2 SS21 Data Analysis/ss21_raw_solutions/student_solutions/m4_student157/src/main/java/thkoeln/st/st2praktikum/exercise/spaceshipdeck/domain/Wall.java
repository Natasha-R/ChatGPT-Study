package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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


    public Wall(Vector2D start, Vector2D end) {

        if (start.isGreaterThan(end)) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
        fetchAxis();

    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getEnd() {
        return end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
        checkIfBarrierStringIsValid(wallString);

        Wall result;
        final StringTokenizer st = new StringTokenizer(wallString, "-");
        final Vector2D _start = Vector2D.fromString(st.nextToken());
        final Vector2D _end = Vector2D.fromString(st.nextToken());

        if (_start.isGreaterThan(_end)) {
            result = new Wall(_start, _end);
        } else {
            result = new Wall(_end, _start);
        }
        fetchAxis(_start, _end); // Throws an exception if the the barrier is neither vertical nor horizontal.
        return result;
    }

    private static String fetchAxis(Vector2D start, Vector2D end) {
        if (start.getY().equals(end.getY())) {
            return "horizontal";
        } else if (start.getX().equals(end.getX())) {
            return "vertical";
        } else {
            throw new RuntimeException("The barrier is neither vertical nor horizontal.");
        }
    }

    private static void checkIfBarrierStringIsValid(String barrierString) {
        Pattern pattern = Pattern.compile("^\\([0-9]+,[0-9]+\\)-\\([0-9]+,[0-9]+\\)$");
        Matcher matcher = pattern.matcher(barrierString);
        if (!matcher.matches()) {
            throw new RuntimeException("\"" + barrierString + "\" is not a valid barrierString in Barrier().");
        }
    }

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

}
