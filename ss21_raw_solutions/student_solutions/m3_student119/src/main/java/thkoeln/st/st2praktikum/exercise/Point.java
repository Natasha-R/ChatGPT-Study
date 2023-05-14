package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class Point {
    private Integer x = 0;
    private Integer y = 0;

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

    public Point() {
        ensureNoNegativePoints();
    }

    private void ensureNoNegativePoints() {
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("negative points are not supported!");
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
