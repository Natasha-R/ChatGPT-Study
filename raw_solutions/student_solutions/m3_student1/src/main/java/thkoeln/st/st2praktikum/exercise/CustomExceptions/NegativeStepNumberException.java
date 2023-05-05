package thkoeln.st.st2praktikum.exercise.CustomExceptions;

public class NegativeStepNumberException extends RuntimeException{
    public NegativeStepNumberException(String message) {
        super(message);
    }
    public NegativeStepNumberException() {
        super();
    }
}
