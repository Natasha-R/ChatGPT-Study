package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private final Integer x;
    private final Integer y;

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