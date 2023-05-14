package thkoeln.st.st2praktikum.exercise.field;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    public int x;
    public int y;

    /**
     * @param coordinateString format "(2,3)"
     * @return the coordinate
     */
    public static Coordinate turnStringToCoordinate(String coordinateString) {
        String[] coordinateArray =
                coordinateString.replace("(", "").replace(")", "").split(",");
        Coordinate coordinate = new Coordinate();
        coordinate.x = Integer.parseInt(coordinateArray[0]);
        coordinate.y = Integer.parseInt(coordinateArray[1]);
        return coordinate;
    }

    public String getCoordinateString() {
        return ("(" + x + "," + y + ")");
    }
}
