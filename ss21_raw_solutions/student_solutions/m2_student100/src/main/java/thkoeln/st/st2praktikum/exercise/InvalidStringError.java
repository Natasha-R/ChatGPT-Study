package thkoeln.st.st2praktikum.exercise;

public class InvalidStringError extends RuntimeException{
    public InvalidStringError(String errorMessage) {
        super(errorMessage);
    }
}
