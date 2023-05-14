package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Setter
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        int[] coordinates = parseCoordinateStringToIntArray(coordinateString);
        if(coordinates[0] < 0 || coordinates[1] < 0) throw new IllegalArgumentException();
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    private int[] parseCoordinateStringToIntArray(String coordinateString) {
        String[] splitString = coordinateString.substring(1, coordinateString.length() - 1).split(",");
        if (splitString.length > 2)
            throw new IllegalArgumentException();
        else
            return Arrays.stream(splitString).mapToInt(s -> {
                int coordinate = Integer.parseInt(s.strip());
                if (coordinate < 0)
                    throw new IllegalArgumentException();
                else
                    return coordinate;
            }).toArray();
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
