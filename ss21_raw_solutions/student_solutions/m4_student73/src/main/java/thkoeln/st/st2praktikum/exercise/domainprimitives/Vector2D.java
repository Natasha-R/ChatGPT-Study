package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.InvalidStringException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.NegativeNumberException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Vector2D {

    private Integer x;
    private Integer y;


    public Vector2D(Integer x, Integer y) throws RuntimeException {
        if (x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
        } else throw new NegativeNumberException("No negative Numbers allowed");
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) throws RuntimeException {
        if (checkStringSyntax(vector2DString)) {
            int xTemp = Integer.parseInt(vector2DString.substring(vector2DString.indexOf('(')+1, vector2DString.indexOf(',')));
            int yTemp = Integer.parseInt(vector2DString.substring(vector2DString.indexOf(',')+1, vector2DString.indexOf(')')));
            if (xTemp >= 0 && yTemp >= 0) {
                this.x = xTemp;
                this.y = yTemp;
            } else throw new NegativeNumberException("No negative Numbers allowed");
        } else throw new InvalidStringException("Please input a correct String");
    }

    public static Vector2D fromString(String s) {
        return new Vector2D(s);
    }

    private boolean checkStringSyntax(String vector2DString) {
        if (vector2DString.charAt(0) == '(' && vector2DString.charAt(vector2DString.length()-1) == ')') {
            if (countOccurrences(vector2DString, ',') == 1 && countOccurrences(vector2DString, '(') == 1 && countOccurrences(vector2DString, ')') == 1) {
                try {
                    Integer.parseInt(vector2DString.substring(1, vector2DString.indexOf(',')));
                    Integer.parseInt(vector2DString.substring(vector2DString.indexOf(',')+1, vector2DString.length()-1));
                    return !vector2DString.contains(" ");
                } catch (NumberFormatException e) {
                    return false;
                }
            } else return false;
        } else return false;

    }

    public Integer countOccurrences(String vector2DString ,char c) {
        int count = 0;
        for (int i = 0; i < vector2DString.length(); i++) {
            if (vector2DString.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
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
