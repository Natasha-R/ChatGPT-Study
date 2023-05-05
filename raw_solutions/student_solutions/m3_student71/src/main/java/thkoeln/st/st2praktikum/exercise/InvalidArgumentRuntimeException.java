package thkoeln.st.st2praktikum.exercise;

public class InvalidArgumentRuntimeException extends RuntimeException {
    public InvalidArgumentRuntimeException() {
        super();
    }
    public InvalidArgumentRuntimeException(String errorMessage) {
        super(errorMessage);
    }
}
