package thkoeln.st.st2praktikum.exercise.Exception;

public class DiagonaleObstacleException extends RuntimeException {
    public DiagonaleObstacleException() {
        super("Obstacle darf nicht diagonal sein");
    }
}
