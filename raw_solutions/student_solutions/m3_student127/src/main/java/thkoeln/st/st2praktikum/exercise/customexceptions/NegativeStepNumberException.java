package thkoeln.st.st2praktikum.exercise.customexceptions;

public class NegativeStepNumberException extends RuntimeException{
    public NegativeStepNumberException(String message) {
        super(message);
    }
    public NegativeStepNumberException() {
        super();
    }
}
