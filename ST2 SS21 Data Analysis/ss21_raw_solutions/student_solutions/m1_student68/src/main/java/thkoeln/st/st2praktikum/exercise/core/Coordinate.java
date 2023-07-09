package thkoeln.st.st2praktikum.exercise.core;

import java.util.Objects;


public class Coordinate {
    private Integer x;
    private Integer y;

     public Coordinate(Integer x, Integer y) {
         this.x = x;
         this.y = y;
     }

     /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
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


    public Coordinate max(Coordinate coordinate) {
       if ( this.getX()>= coordinate.getX() && this.getY()>= coordinate.getY())
           return this;
       else if ( this.getX()<= coordinate.getX() && this.getY()<= coordinate.getY() )
           return coordinate;
       else
           throw new IllegalArgumentException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + this + "  \n  " +
               "Position2: " + coordinate + "  \n  ");
     }

    public Coordinate min(Coordinate coordinate) {
       if ( this.getX()>= coordinate.getX() && this.getY()>= coordinate.getY() )
           return coordinate;
       else if ( this.getX()<= coordinate.getX() && this.getY()<= coordinate.getY() )
           return this;
       throw new IllegalArgumentException(
               "Positions comparison issue to calculate maximum. \n " +
               "Position1: " + this + "  \n  " +
               "Position2: " + coordinate + "  \n  ");
     }

}
