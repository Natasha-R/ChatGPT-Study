package thkoeln.st.st2praktikum.exercise.CustomExceptions;

public class DiagonalBarrierException extends RuntimeException{
    public DiagonalBarrierException(String message) {
        super(message);
    }
    public DiagonalBarrierException() {
        super();
    }
}
