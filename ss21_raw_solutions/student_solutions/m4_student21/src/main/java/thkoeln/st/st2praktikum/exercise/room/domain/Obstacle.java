package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor
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
    public static Obstacle fromString(String obstacleString) {
        Coordinate coordinateArray[] = getCoordinatesFromString(obstacleString);
        validateInput(coordinateArray[0], coordinateArray[1]);
        Coordinate start = coordinateArray[0];
        Coordinate end = coordinateArray[1];
        Obstacle obstacle = new Obstacle(start, end);
        return obstacle;
    }

    public static Coordinate[] getCoordinatesFromString(String obstacleString){
        if (obstacleString.matches("\\(\\d+\\,\\d+\\)\\-\\(\\d+\\,\\d+\\)")) {
            String[] obstacleStringArray = obstacleString.split("-");
            Coordinate start =  Coordinate.fromString(obstacleStringArray[0]);
            Coordinate end = Coordinate.fromString(obstacleStringArray[1]);
;
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

    private static void validateInput(Coordinate start, Coordinate end){
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

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
