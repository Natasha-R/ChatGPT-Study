package thkoeln.st.st2praktikum.exercise.space.domain.exception;

public class DiagonaleObstacleException extends RuntimeException {
    public DiagonaleObstacleException() {
        super("Obstacle darf nicht diagonal sein");
    }
}
