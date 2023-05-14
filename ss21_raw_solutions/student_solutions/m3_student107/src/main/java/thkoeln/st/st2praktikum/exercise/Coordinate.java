package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(!(x >= 0 && y >= 0)) throw new RuntimeException("Coordinate has negative");

        this.x = x;
        this.y = y;
    }

    public Coordinate(Integer x, Integer y , boolean cannegativ){
        if(!cannegativ) if(!(x >= 0 && y >= 0)) throw new RuntimeException("Coordinate has negative");

        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString){
        String tmp = coordinateString.replace("(","").replace(")","");
        String[] data = tmp.split(",");

        if(data.length==2) {

            if (!(Integer.parseInt(data[0]) >= 0 && Integer.parseInt(data[1]) >= 0))
                throw new RuntimeException("Coordinate has negative");

            this.x = Integer.parseInt(data[0]);
            this.y = Integer.parseInt(data[1]);
        } else throw new RuntimeException("Invalid Command");
    }
    public Coordinate(Coordinate coordinate){
        this.y = coordinate.y;
        this.x = coordinate.x;
    }

    public Coordinate(){
        this.x = 0;
        this.y = 0;
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

    public boolean shareXAxis(Coordinate coordinate){
        return this.getX().equals(coordinate.getX());
    }
    public boolean shareYAxis(Coordinate coordinate){
        return this.getY().equals(coordinate.getY());
    }
}
