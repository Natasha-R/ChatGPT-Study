package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;


@Setter
@Getter
@Embeddable
@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
    }

    protected Coordinate(){};

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
        return new Coordinate(Integer.parseInt(coordinateString.substring(1,coordinateString.lastIndexOf(","))),
                Integer.parseInt(coordinateString.substring(coordinateString.lastIndexOf(",") + 1, coordinateString.length()-1)));
    }
    
}
