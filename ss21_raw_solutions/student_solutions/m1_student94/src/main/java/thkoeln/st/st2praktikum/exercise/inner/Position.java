package thkoeln.st.st2praktikum.exercise.inner;

import lombok.EqualsAndHashCode;
import thkoeln.st.st2praktikum.exercise.space.Walkable;
@EqualsAndHashCode
public class Position {
    private final Walkable space;
    private final int xValue;
    private final int yValue;

    public Position(Walkable space, Integer xValue, Integer yValue) {
        this.space=space;
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public Position(Walkable space, String coordinate) {
        String[] dividedCoordinate=coordinate.substring(1,coordinate.length()-1).split(",");
        this.xValue = Integer.parseInt(dividedCoordinate[0]);
        this.yValue = Integer.parseInt(dividedCoordinate[1]);
        this.space=space;
    }

    public Position max(Position position) {
        if ( this.getX()>=position.getX() && this.getY()>=position.getY() && this.getSpace() == position.getSpace() )
            return this.space.getLastPosition(position);
        else if ( this.getX()<=position.getX() && this.getY()<=position.getY() && this.getSpace() == position.getSpace() )
            return position;
        throw new IllegalArgumentException(
                "Too big Positions. \n " );
    }

    public Position min(Position position) {
        if ( this.getX()>=position.getX() && this.getY()>=position.getY() && this.getSpace() == position.getSpace() )
            return position;
        else if ( this.getX()<=position.getX() && this.getY()<=position.getY() && this.getSpace() == position.getSpace() )
            return this;
        throw new IllegalArgumentException(
                "Too minimal Positions. \n " );
    }

    public int getX() { return this.xValue; }

    public int getY() { return this.yValue; }

    public Walkable getSpace() { return this.space; }

    @Override
    public String toString() { return "(" + this.xValue + ","+ this.yValue + ")"; }
}


