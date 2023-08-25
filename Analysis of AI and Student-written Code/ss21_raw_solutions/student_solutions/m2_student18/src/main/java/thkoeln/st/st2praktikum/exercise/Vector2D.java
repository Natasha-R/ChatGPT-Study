package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Objects;


public class Vector2D {

    @Setter
    @Getter
    private Integer x;
    @Setter
    @Getter
    private Integer y;


    public Vector2D(Integer x, Integer y) {
        if (x<0 || y<0)
            throw new RuntimeException("Invalid Numbers");
        else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) {
        if (vector2DString.isEmpty())
            throw new RuntimeException("Empty vector2DString");
        if (!(vector2DString.startsWith("(") && vector2DString.endsWith(")")) || StringUtils.countOccurrencesOf(vector2DString, ",")!=1)
            throw new RuntimeException("Invalid vector2DString");

        String[] input = vector2DString.replace("(","").replace(")","").split(",");

        if (Integer.parseInt(input[0])<0 || Integer.parseInt(input[1])<0)
            throw new RuntimeException("Invalid Numbers");
        else {
            x = Integer.parseInt(input[0]);
            y = Integer.parseInt(input[1]);
        }
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

}