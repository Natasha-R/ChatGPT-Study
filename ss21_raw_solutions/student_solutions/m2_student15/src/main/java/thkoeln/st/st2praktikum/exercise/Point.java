package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if(x>=0 && y>=0) {
            this.x = x;
            this.y = y;
        }else{
            throw new RuntimeException("Negative numbers not allowed here");
        }
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        if(pointString.matches("\\({1}\\d+\\,\\d+\\){1}")){
            String[] parts = pointString.split(",");

            int first = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
            int second = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));

                this.x = first;
                this.y = second;
        }else{
            throw new RuntimeException("Wrong Syntax or negative number");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
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
