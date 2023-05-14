package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Setter
@EqualsAndHashCode
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if(x < 0 || y < 0) throw new RuntimeException();
        this.x = x;
        this.y = y;
    }

public Point() { }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public static Point fromString(String pointString) {
        final String regex = "\\(([0-9]*),([0-9]*)\\)";
        int parsedY;
        int parsedX;

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(pointString);

        if (matcher.find()) {
            parsedX = Integer.parseInt(matcher.group(1));
            parsedY = Integer.parseInt(matcher.group(2));
        } else {
            throw new RuntimeException();
        }

        if(parsedX < 0 || parsedY < 0) {
            throw new RuntimeException();
        }
        return new Point(parsedX, parsedY);
    }

    public Boolean isGreaterThan(Point that) {
        return this.getX().equals(that.getX()) && this.getY() > that.getY()
                || this.getY().equals(that.getY()) && this.getX() > that.getX();
    }
}
