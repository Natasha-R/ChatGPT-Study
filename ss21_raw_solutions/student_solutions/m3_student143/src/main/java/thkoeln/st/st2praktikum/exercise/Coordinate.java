package thkoeln.st.st2praktikum.exercise;



import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Access(AccessType.FIELD)
public class Coordinate {
    int x;
    int y;

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (!coordinateString.matches("\\(\\d+,\\d+\\)")) {
            throw new IllegalArgumentException("The coordinate string is not properly formatted");
        }
        String[] xy = coordinateString
                .replaceAll("\\(|\\)", "")
                .split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        ensureValidCoordinateInput(x,y);
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y) {
        ensureValidCoordinateInput(x,y);
        this.x = x;
        this.y = y;
    }

    void ensureValidCoordinateInput(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("The coordinate shall be positive");
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

