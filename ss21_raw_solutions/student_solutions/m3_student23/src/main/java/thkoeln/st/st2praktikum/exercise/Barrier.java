package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
public class Barrier {

    private Point start;
    private Point end;
    private String axis;

    protected Barrier() {}

    public Barrier(Point pos1, Point pos2) {
        this.orderAndSetPointsAndAxis(pos1, pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {
        Pattern barrierStringPattern = Pattern.compile("^(\\(\\d,\\d\\))-(\\(\\d,\\d\\))$");
        Matcher barrierStringMatcher = barrierStringPattern.matcher(barrierString);

        String start = null, end = null;

        while (barrierStringMatcher.find()) {
            start = barrierStringMatcher.group(1);
            end = barrierStringMatcher.group(2);
        }

        this.createBarrierFromPointStrings(start, end);
    }

    private String generateAxis(Point start, Point end) {
        if (start.getX().equals(end.getX())) return "x";
        if (start.getY().equals(end.getY())) return "y";
        throw new InvalidParameterException("Diagonal Axis are not supported");
    }

    private void createBarrierFromPointStrings(String start, String end) throws InvalidParameterException {
        Point startPoint = new Point(start);
        Point endPoint = new Point(end);

        this.orderAndSetPointsAndAxis(startPoint, endPoint);
    }

    private void orderAndSetPointsAndAxis(Point start, Point end) {
        this.axis = this.generateAxis(start, end);

        if (start.getX() < end.getX() || start.getY() < end.getY()) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public String getAxis() {
        return axis;
    }
}
