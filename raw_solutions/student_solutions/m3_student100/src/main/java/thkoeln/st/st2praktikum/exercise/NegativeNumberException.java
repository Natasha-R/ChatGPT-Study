package thkoeln.st.st2praktikum.exercise;

public class NegativeNumberException extends RuntimeException{
    public NegativeNumberException(String errorMessage) {
        super(errorMessage);
    }
}
