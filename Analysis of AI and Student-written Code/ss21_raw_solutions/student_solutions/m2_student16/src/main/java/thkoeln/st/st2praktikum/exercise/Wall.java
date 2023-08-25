package thkoeln.st.st2praktikum.exercise;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
        if(start.getX() + start.getY() > end.getX() + end.getY()){
            this.start = pos2;
            this.end = pos1;
        }
        if(!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) throw new IllegalArgumentException();

    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        this(
                new Coordinate(wallString.substring(0,wallString.lastIndexOf('-'))),
                new Coordinate(wallString.substring(wallString.lastIndexOf('-') + 1))
        );
    }

    public boolean isHorizontal(){
        return start.getY().equals(end.getY());
    }

    public boolean isVertical(){
        return start.getX().equals(end.getX());
    }

    public boolean isNorthOf(Coordinate coordinate){
        return isHorizontal() && coordinate.getY() == start.getY() - 1 && coordinate.getX() > start.getX() && coordinate.getX() < end.getX();
    }
    public boolean isSouthOf(Coordinate coordinate){
        return isHorizontal() && coordinate.getY() == start.getY() + 1 && coordinate.getX() > start.getX() && coordinate.getX() < end.getX();
    }

    public boolean isWestOf(Coordinate coordinate){
        return isVertical() && coordinate.getX() == start.getX() + 1 && coordinate.getY() > start.getY() && coordinate.getY() < end.getY();
    }

    public boolean isEastOf(Coordinate coordinate){
        return isVertical() && coordinate.getX() == start.getX() - 1 && coordinate.getY() > start.getY() && coordinate.getY() < end.getY();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }



}
