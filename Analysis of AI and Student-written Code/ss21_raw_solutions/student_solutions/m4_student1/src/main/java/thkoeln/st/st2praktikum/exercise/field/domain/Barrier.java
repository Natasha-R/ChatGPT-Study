package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

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
public class Barrier {

    @Embedded
    private Point start;
    @Embedded
    private Point end;


    public Barrier(Point start, Point end) {

        if (start.isGreaterThan(end)) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
        fetchAxis();

    }
    
    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        checkIfBarrierStringIsValid(barrierString);

        Barrier result;
        final StringTokenizer st = new StringTokenizer(barrierString, "-");
        final Point _start = Point.fromString(st.nextToken());
        final Point _end = Point.fromString(st.nextToken());

        if (_start.isGreaterThan(_end)) {
            result = new Barrier(_start, _end);
        } else {
            result = new Barrier(_end, _start);
        }
        fetchAxis(_start, _end); // Throws an exception if the the barrier is neither vertical nor horizontal.
        return result;
    }

    private static String fetchAxis(Point start, Point end) {
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

    public Boolean isBlocking(Point currentPoint, Point nextPoint) {
        if (currentPoint.isGreaterThan(nextPoint)) {
            Point temp = currentPoint;
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
