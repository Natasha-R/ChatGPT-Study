package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        if (x < 0 || y < 0) throw new IllegalArgumentException("Can't add a negative coordinate!");
        else {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (coordinateString.matches("[(]+[0-9]+[,]+[0-9]+[)]")) {
            String coordinateUnStrung = coordinateString.replace("(", "");
            coordinateUnStrung = coordinateUnStrung.replace(")", "");
            String[] barrierArray = coordinateUnStrung.split(",");

            int[] coordinateArray = new int[barrierArray.length];
            for (int i = 0; i < barrierArray.length; i++) {
                coordinateArray[i] = Integer.parseInt(barrierArray[i].trim());
            }
            this.x = coordinateArray[0];
            this.y = coordinateArray[1];
        } else throw new IllegalArgumentException("Not a valid input");
    }

    protected Coordinate() {
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
