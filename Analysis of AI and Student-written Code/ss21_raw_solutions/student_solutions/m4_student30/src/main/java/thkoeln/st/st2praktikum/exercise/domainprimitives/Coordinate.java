package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (y < 0 || x < 0) {
            throw new RuntimeException("Negative Koordinaten nicht zulaessig");
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {

        if (coordinateString == null) {
            throw new UnsupportedOperationException();
        }
        if (!coordinateString.matches("\\(\\d,\\d\\)")) {
            throw new RuntimeException("Falscher Eingabestring");

        }

        String[] parts = coordinateString.split(Pattern.quote(","));


        int xcheck = Integer.parseInt(parts[0].replaceAll("\\D", ""));
        int ycheck = Integer.parseInt(parts[1].replaceAll("\\D", ""));
        if (xcheck < 0 || ycheck < 0) {
            throw new RuntimeException("Negative Coordinate");
        }
        return  new Coordinate(xcheck,ycheck);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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
