package thkoeln.st.st2praktikum.exercise.obstacle;

import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.coordinate.MyCoordinate;
import thkoeln.st.st2praktikum.exercise.coordinate.MyCoordinateInterface;

public class MyObstacle implements ObstacleInterface {
    private final MyCoordinateInterface start;
    private final MyCoordinateInterface end;

    public MyObstacle(Obstacle obstacle) {
        this.start = new MyCoordinate(obstacle.getStart());
        this.end = new MyCoordinate(obstacle.getEnd());
    }

    @Override
    public Integer getStartX() {
        return start.getX();
    }

    @Override
    public Integer getStartY() {
        return start.getY();
    }

    @Override
    public Integer getEndX() {
        return end.getX();
    }

    @Override
    public Integer getEndY() {
        return end.getY();
    }

    @Override
    public Boolean isEqualObstacle(ObstacleInterface obstacle) {
        return this.getStartX().equals(obstacle.getStartX()) &&
                this.getStartY().equals(obstacle.getStartY()) &&
                this.getEndX().equals(obstacle.getEndX()) &&
                this.getEndY().equals(obstacle.getEndY());
    }
}
