package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@Embeddable
public class Obstacle {

    @Getter
    @Embedded
    private Coordinate start;
    @Getter
    @Embedded
    private Coordinate end;

    private Alignment alignment;

    public Obstacle(Coordinate pos1, Coordinate pos2) {
        validateInput(pos1, pos2);
        this.start = pos1;
        this.end = pos2;
        setCoordinatesInOrder();
        setAlignment();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        Coordinate coordinateArray[] = getCoordinatesFromString(obstacleString);
        validateInput(coordinateArray[0], coordinateArray[1]);
        this.start = coordinateArray[0];
        this.end = coordinateArray[1];
        setCoordinatesInOrder();
        setAlignment();
    }

    public Coordinate[] getCoordinatesFromString(String obstacleString){
        if (obstacleString.matches("\\(\\d+\\,\\d+\\)\\-\\(\\d+\\,\\d+\\)")) {
            String[] obstacleStringArray = obstacleString.split("-");
            Coordinate start = new Coordinate(obstacleStringArray[0]);
            Coordinate end = new Coordinate(obstacleStringArray[1]);
            return new Coordinate[]{start, end};
        }
        throw new RuntimeException("This ObstacleString is in the wrong format! Your obstacleString: " + obstacleString);
    }

    public boolean pointCollision(Coordinate coordinate){
        return (xCollision(coordinate) && yCollision(coordinate));
    }

    private boolean yCollision(Coordinate coordinate){
        return (start.getY() <= coordinate.getY() && coordinate.getY() <= end.getY());
    }

    private boolean xCollision(Coordinate coordinate){
        return (start.getX() <= coordinate.getX() && coordinate.getX() <= end.getX());
    }

    private void validateInput(Coordinate start, Coordinate end){
        if(start.getX() == end.getX() && start.getY() == end.getY()){
            throw new RuntimeException("Obstacles cannot be a point!");
        }
        if(start.getX() != end.getX() && start.getY() != end.getY()){
            throw new RuntimeException("Obstacles cannot be diagonal!");
        }
    }

    private void setAlignment(){
        if(start.getX().equals(end.getX())){
            alignment = Alignment.VERTICAL;
        }else if(start.getY().equals(end.getY())){
            alignment = Alignment.HORIZONTAL;
        }else{
            throw new RuntimeException("Failed to set alignment of Obstacle! Diagonal obstacles not allowed!");
        }
    }

    private void setCoordinatesInOrder(){
        if(end.getX() < start.getX() || end.getY() < start.getY()){
            Coordinate tmp = end;
            end = start;
            start = tmp;
        }
    }
}
