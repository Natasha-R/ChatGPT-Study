package thkoeln.st.st2praktikum.exercise.CustomExceptions;

public class NotAWalkCommandException extends RuntimeException{
    public NotAWalkCommandException(String message) {
        super(message);
    }
    public NotAWalkCommandException() {
        super();
    }
}
