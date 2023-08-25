package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class NegativeNumberException extends RuntimeException{
    public NegativeNumberException(String errorMessage) {
        super(errorMessage);
    }
}
