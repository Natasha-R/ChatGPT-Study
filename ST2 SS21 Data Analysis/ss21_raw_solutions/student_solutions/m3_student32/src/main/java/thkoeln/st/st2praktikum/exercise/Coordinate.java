package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y)  {
        if (x < 0 || y < 0){
            throw new RuntimeException("x and y must be greater or equal than 0");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (!coordinateString.matches("\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}")){
            throw new RuntimeException("Invalid coordinateString");
        }
        String cleanedCoordinateString = coordinateString.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] sourceCoordinateArray = cleanedCoordinateString.split(",");
        int x = Integer.parseInt(sourceCoordinateArray[0]);
        int y = Integer.parseInt(sourceCoordinateArray[1]);
        if (x < 0 || y < 0) {
            throw new RuntimeException("x and y must be greater or equal than 0");
        }
        this.x = x;
        this.y = y;

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

}
