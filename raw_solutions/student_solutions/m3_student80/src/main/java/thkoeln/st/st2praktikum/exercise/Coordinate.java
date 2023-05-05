package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if (x < 0 || y < 0) {
            throw new NegativNumberExeption("Es sind keine negativen Zahlen erlaubt");
        } else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        var firstChar = Character.getType(coordinateString.charAt(1));
        var secondChar = Character.getType(coordinateString.charAt(3));
        String testString = coordinateString;
        Integer compareInt = 1;
        if (coordinateString.charAt(0) != '(' || Integer.class.isInstance(firstChar) != Integer.class.isInstance(compareInt) || coordinateString.charAt(2) != ',' || Integer.class.isInstance(secondChar) != Integer.class.isInstance(compareInt) || coordinateString.charAt(4) != ')') {
            throw new InvalidStringError("Dies ist kein zugelassener String");
        } else {
            coordinateString = coordinateString.replaceAll("[()]", "");
            String[] coordinateStrings = coordinateString.split(",");
            for (int i = 0; i < 1; i++) {
                if (testString.charAt(1) == '-' || testString.charAt(3) == '-') {
                    throw new NegativNumberExeption("Es sind keine negativen Zahlen erlaubt");
                } else {
                    x = Integer.parseInt(coordinateStrings[0]);
                    y = Integer.parseInt(coordinateStrings[1]);
                }
            }
        }
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
