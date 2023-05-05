package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import javax.persistence.Embeddable;
import java.util.Objects;

import java.util.ArrayList;
import java.util.Iterator;


//@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Getter
@Setter ( AccessLevel.PROTECTED )
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
         if (x<0 || y<0)
             throw new CoordinateException("Negative Coordinate: ("+x+","+y+")");
         this.x = x;
         this.y = y;
     }

     /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if ( !coordinateString.matches("\\([0-9]+,[0-9]+\\)") )
            throw new CoordinateException("Invalid Coordinate String: " + coordinateString );
        String[] fragmentedCoordinate = coordinateString.replaceAll("[()]","").split(",");
        this.x = Integer.parseInt(fragmentedCoordinate[0]);
        this.y = Integer.parseInt(fragmentedCoordinate[1]);
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
    public int hashCode() { return Objects.hash(x, y); }

    @Override
    public String toString() { return "(" + this.x + ","+ this.y + ")"; }

    public Integer getX() { return x; }

    public Integer getY() { return y; }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        return new Coordinate(coordinateString);
    }


      public Coordinate min(Coordinate coordinate) {
       if ( this.getX()>= coordinate.getX() && this.getY()>= coordinate.getY() )
           return coordinate;
       else if ( this.getX()<= coordinate.getX() && this.getY()<= coordinate.getY() )
           return this;
       throw new CoordinateException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + this + "  \n  " +
               "Position2: " + coordinate + "  \n  ");
     }

    public Coordinate max(Coordinate coordinate) {
       if ( this.getX()>= coordinate.getX() && this.getY()>= coordinate.getY())
           return this;
       else if ( this.getX()<= coordinate.getX() && this.getY()<= coordinate.getY() )
           return coordinate;
       else
           throw new CoordinateException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + this + "  \n  " +
               "Position2: " + coordinate + "  \n  ");
     }

    public static Coordinate min(Coordinate coordinate1, Coordinate coordinate2) {
       if ( coordinate1.getX()>= coordinate2.getX() && coordinate1.getY()>= coordinate2.getY() )
           return coordinate2;
       else if ( coordinate1.getX()<= coordinate2.getX() && coordinate1.getY()<= coordinate2.getY() )
           return coordinate1;
       throw new CoordinateException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + coordinate1 + "  \n  " +
               "Position2: " + coordinate2 + "  \n  ");
     }

    public static Coordinate max(Coordinate coordinate1, Coordinate coordinate2) {
       if ( coordinate1.getX()>= coordinate2.getX() && coordinate1.getY()>= coordinate2.getY())
           return coordinate1;
       else if ( coordinate1.getX()<= coordinate2.getX() && coordinate1.getY()<= coordinate2.getY() )
           return coordinate2;
       else
           throw new CoordinateException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + coordinate1 + "  \n  " +
               "Position2: " + coordinate2 + "  \n  ");
     }
}
