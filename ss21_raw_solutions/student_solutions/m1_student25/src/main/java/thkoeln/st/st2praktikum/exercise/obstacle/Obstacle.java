package thkoeln.st.st2praktikum.exercise.obstacle;

import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;

public class Obstacle implements ObstacleInterface {
    private CoordinateInterface start;
    private CoordinateInterface end;

    public Obstacle(CoordinateInterface start, CoordinateInterface end){
        this.start = start;
        this.end = end;
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
        return  this.getStartX().equals(obstacle.getStartX())   &&
                this.getStartY().equals(obstacle.getStartY())   &&
                this.getEndX().equals(obstacle.getEndX()) &&
                this.getEndY().equals(obstacle.getEndY());
    }
}
