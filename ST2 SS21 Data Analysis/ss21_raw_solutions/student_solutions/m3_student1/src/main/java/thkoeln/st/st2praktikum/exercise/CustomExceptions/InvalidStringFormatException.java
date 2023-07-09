package thkoeln.st.st2praktikum.exercise.CustomExceptions;

public class InvalidStringFormatException extends RuntimeException {
    public InvalidStringFormatException(String message) {
        super(message);
    }
    public InvalidStringFormatException() {
        super();
    }
}
