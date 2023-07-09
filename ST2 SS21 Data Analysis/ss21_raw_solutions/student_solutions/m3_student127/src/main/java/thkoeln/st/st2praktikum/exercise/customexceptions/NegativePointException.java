package thkoeln.st.st2praktikum.exercise.customexceptions;

public class NegativePointException extends RuntimeException{
    public NegativePointException(String message) {
        super(message);
    }
    public NegativePointException() {
        super();
    }
}
