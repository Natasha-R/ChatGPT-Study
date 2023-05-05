package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        ensureNoNegativePoints();
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        final Pattern pattern = Pattern.compile("^\\((\\d+),(\\d+)\\)$");
        final Matcher matcher = pattern.matcher(pointString);

        if(matcher.find()) {
            final String x = matcher.group(1);
            final String y = matcher.group(2);

            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
            ensureNoNegativePoints();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void ensureNoNegativePoints() {
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("negative points are not supported!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

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
}
