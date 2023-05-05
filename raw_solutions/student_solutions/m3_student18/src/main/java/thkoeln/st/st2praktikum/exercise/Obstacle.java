package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@EqualsAndHashCode
public class Obstacle {

    @Getter
    @Embedded
    private Vector2D start;

    @Getter
    @Embedded
    private Vector2D end;

    public Obstacle(int startObstacleX, int startObstacleY, int endObstacleX, int endObstacleY){
        start = new Vector2D(startObstacleX, startObstacleY);
        end = new Vector2D(endObstacleX, endObstacleY);
    }

    public Obstacle(Vector2D pos1, Vector2D pos2) {
        this.start = pos1;
        this.end = pos2;
        if (start.getX()+start.getY()>end.getX()+end.getY())
            changeOrder();
        if (isDiagonal())
            throw new RuntimeException("Not valid Line");
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString){
        if (obstacleString.isEmpty())
            throw new RuntimeException("Empty vector2DString");
        if (!obstacleString.contains("-"))
            throw new RuntimeException("Invalid Command String");

        String[] input = obstacleString.split("-");

        start = new Vector2D(input[0]);
        end = new Vector2D(input[1]);

        if (start.getX()+start.getY()>end.getX()+end.getY())
            changeOrder();

        if (start.equals(end))
            throw new RuntimeException("Not a Line");

        if (isDiagonal())
            throw new RuntimeException("Not valid Line");
    }

    protected Obstacle(){}

    private void changeOrder(){
        Vector2D tmpVector;
        tmpVector = start;
        start = end;
        end = tmpVector;
    }

    private Boolean isDiagonal(){
        if ( start.getX().equals(end.getX()) && start.getY()<end.getY() )
            return false;
        else if ( start.getY().equals(end.getY()) && start.getX()<end.getX() )
            return false;
        else
            return true;
    }

    public Boolean isObstacle(Vector2D coordinates , CommandType direction){
        if (direction == CommandType.NORTH && start.getY() == (coordinates.getY()+1) && coordinates.getX() >= start.getX() && coordinates.getX() < end.getX())
            return true;
        else if (direction == CommandType.EAST && start.getX() == (coordinates.getX()+1) && coordinates.getY() >= start.getY() && coordinates.getY() < end.getY())
            return true;
        else if (direction == CommandType.SOUTH && start.getY() == coordinates.getY() && coordinates.getX() >= start.getX() && coordinates.getX() < end.getX())
            return true;
        else if (direction == CommandType.WEST && start.getX() == coordinates.getX() && coordinates.getY() >= start.getY() && coordinates.getY() < end.getY())
            return true;
        else
            return false;
    }

}

