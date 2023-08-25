package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


@EqualsAndHashCode
@Setter
@NoArgsConstructor
@Embeddable
public class Coordinate {

    @Column(insertable = false, updatable = false)
    private Integer x;
    @Column(insertable = false, updatable = false)
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if (x>=0 && y>=0) {
            this.x = x;
            this.y = y;
        }else throw new RuntimeException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        Integer x = Integer.parseInt(String.valueOf(coordinateString.charAt(1)));
        Integer y =  Integer.parseInt(String.valueOf(coordinateString.charAt(3)));

        // Is the String correct?
        if (coordinateString.length()>=5 && coordinateString.replaceAll("[0-9]","").equals("(,)") && coordinateString.replaceAll("[^0-9]","").length()==2){
            if (x >=0 && y >=0) {
                return new Coordinate(x,y);
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public String toString() { return "(" + x + ", " + y + ")"; }
    public Integer getX() { return x; }
    public Integer getY() { return y; }


}