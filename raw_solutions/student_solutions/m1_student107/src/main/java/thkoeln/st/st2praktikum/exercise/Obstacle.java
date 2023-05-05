package thkoeln.st.st2praktikum.exercise;

public class Obstacle {
    private Coordinate cooredinateFrom;
    private Coordinate coorddinateTo;

    Obstacle(String obstacleString){
        String[] coordinates = obstacleString.split("-");
        this.cooredinateFrom = new Coordinate(coordinates[0]);
        this.coorddinateTo = new Coordinate(coordinates[1]);
    }

    public Coordinate getCoordinateTo() {
        return coorddinateTo;
    }

    public Coordinate getCooredinateFrom() {
        return cooredinateFrom;
    }

    public boolean isHorizontal(){
        return this.cooredinateFrom.getY() == this.coorddinateTo.getY() && this.coorddinateTo.getX() - this.cooredinateFrom.getX() != 0;
    }
    public boolean isVertical(){
        return this.cooredinateFrom.getX() == this.coorddinateTo.getX() && this.coorddinateTo.getY() - this.cooredinateFrom.getY() != 0;
    }
    public boolean inYrange(Coordinate coordinate){
        return coordinate.getY() >= this.cooredinateFrom.getY() && coordinate.getY() < this.coorddinateTo.getY();
    }

    public boolean inXrange(Coordinate coordinate){
        return coordinate.getX() >= this.cooredinateFrom.getX() && coordinate.getX() < this.coorddinateTo.getX();
    }



}
