package thkoeln.st.st2praktikum.exercise.Exception;

public class WrongOrderException extends RuntimeException {
    public WrongOrderException(String message){
        super(message);
    }
}
