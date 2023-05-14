package thkoeln.st.st2praktikum.exercise.customexceptions;

public class InvalidStringFormatException extends RuntimeException {
    public InvalidStringFormatException(String message) {
        super(message);
    }
    public InvalidStringFormatException() {
        super();
    }
}
