package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class InvalidArgumentRuntimeException extends RuntimeException {
    public InvalidArgumentRuntimeException() {
        super();
    }
    public InvalidArgumentRuntimeException(String errorMessage) {
        super(errorMessage);
    }
}
