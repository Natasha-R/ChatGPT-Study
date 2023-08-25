package thkoeln.st.st2praktikum.exercise.valueObjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    private int x;
    private int y;

    public static Coordinate parseCoordinate(String coordinateString) {
        String[] splitString = coordinateString.split(",");
        return new Coordinate(
                Integer.parseInt(splitString[0].substring(1).strip()),
                Integer.parseInt(splitString[1].substring(0, splitString[1].length() - 1).strip())
        );
    }
}
