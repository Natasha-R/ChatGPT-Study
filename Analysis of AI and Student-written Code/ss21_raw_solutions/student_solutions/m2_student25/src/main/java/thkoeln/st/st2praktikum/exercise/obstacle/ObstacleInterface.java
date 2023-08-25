package thkoeln.st.st2praktikum.exercise.obstacle;

public interface ObstacleInterface {

    Integer getStartX();

    Integer getStartY();

    Integer getEndX();

    Integer getEndY();

    Boolean isEqualObstacle(ObstacleInterface obstacle);
}
