package thkoeln.st.st2praktikum.exercise.CustomExceptions;

public class NegativePointException extends RuntimeException{
    public NegativePointException(String message) {
        super(message);
    }
    public NegativePointException() {
        super();
    }
}
