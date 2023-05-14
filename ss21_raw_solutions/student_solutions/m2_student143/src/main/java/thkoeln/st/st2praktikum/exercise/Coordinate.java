package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        initialize(x,y);
    }

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
        initialize(x,y);
    }


    private void initialize(Integer x, Integer y){
        this.x = x;
        this.y = y;
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("The coordinate shall be positive");
        }
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
        return "(" + x + "," + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
