package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        if (coordinateString.matches("[(]+[0-9]+[,]+[0-9]+[)]")) {
            String coordinateUnStrung = coordinateString.replace("(", "");
            coordinateUnStrung = coordinateUnStrung.replace(")", "");
            String[] barrierArray = coordinateUnStrung.split(",");

            int[] coordinateArray = new int[barrierArray.length];
            for (int i = 0; i < barrierArray.length; i++) {
                coordinateArray[i] = Integer.parseInt(barrierArray[i].trim());
            }
            Integer x = coordinateArray[0];
            Integer y = coordinateArray[1];
            return new Coordinate(x, y);
        } else throw new IllegalArgumentException("Not a valid input");
    }

    protected Coordinate() {
    }

}
