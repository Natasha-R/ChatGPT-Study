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
        if(x < 0 || y < 0) {
            throw new NegativeNumberException("Negative Zahlen sind nicht erlaubt");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        int firstNumber = Character.getNumericValue(coordinateString.charAt(1));
        int secondNumber = Character.getNumericValue(coordinateString.charAt(3));
        Integer.class.isInstance(firstNumber);
        int comparenumber = 0;
        String testString = coordinateString;


        if(coordinateString.charAt(0) != '('
                || Integer.class.isInstance(firstNumber) != Integer.class.isInstance(comparenumber)
                || coordinateString.charAt(2) != ','
                || Integer.class.isInstance(secondNumber) != Integer.class.isInstance(comparenumber)
                || coordinateString.charAt(4) != ')') {
            throw new InvalidStringError("Dies ist kein erlaubter String");
        } else {
            coordinateString = coordinateString.replaceAll("[()]", "");
            String[] coordinatesStrings = coordinateString.split(",");
            for (int i = 0; i < 1; i++) {
                if (testString.charAt(1) == '-' || testString.charAt(3) == '-') {
                    throw new NegativeNumberException("Negative Zahlen sind nicht erlaubt");
                } else {
                    x = Integer.parseInt(
                            coordinatesStrings[0].replaceAll("[(]", ""));
                    y = Integer.parseInt(
                            coordinatesStrings[1].replaceAll("[)]", ""));
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
