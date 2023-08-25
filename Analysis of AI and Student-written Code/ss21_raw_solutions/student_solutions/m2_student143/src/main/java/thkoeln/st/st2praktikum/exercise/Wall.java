package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Wall implements FieldItem, Collidable {

    private Coordinate start;
    private Coordinate end;

    public Wall(Coordinate pos1, Coordinate pos2) {
        initialize(pos1,pos2);
    }


    private void initialize(Coordinate start, Coordinate end){
        boolean firstCoordinateIsBottomLeftCorner =
                start.getX() <= end.getX() && start.getY() <= end.getY();
        if(!firstCoordinateIsBottomLeftCorner){
            Coordinate tmp = start;
            start = end;
            end = tmp;
        }
        if(start.getX() < end.getX() && !start.getY().equals(end.getY())){
            throw new IllegalArgumentException(
                    "a wall must be either vertical or horizontal"
            );
        }
        if(start.getY() < end.getY() && !start.getX().equals(end.getX())){
            throw new IllegalArgumentException(
                    "a wall must be either vertical or horizontal"
            );
        }
        this.start = start;
        this.end = end;
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        String [] coordinateStrings = wallString.split("-");
        Coordinate start = new Coordinate(coordinateStrings[0]);
        Coordinate end = new Coordinate(coordinateStrings[1]);

        String expectedInputString = start.toString() + "-" + end.toString();
        if(!expectedInputString.equals(wallString)){
            throw new IllegalArgumentException("the wall string was ill-formatted");
        }
        initialize(start,end);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }


    @Override
    public Coordinate getCoordinate() { return start; }

    @Override
    public Rectangle getBounds() { return Rectangle.fromCoordinates(start,end); }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Objects.equals(start, wall.start) && Objects.equals(end, wall.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString(){
        return start.toString() + "-" + end.toString();
    }
}
