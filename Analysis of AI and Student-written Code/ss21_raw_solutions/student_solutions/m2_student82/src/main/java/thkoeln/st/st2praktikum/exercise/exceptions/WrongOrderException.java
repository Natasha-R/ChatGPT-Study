package thkoeln.st.st2praktikum.exercise.exceptions;

public class WrongOrderException extends RuntimeException {
    public WrongOrderException(String message){
        super(message);
    }
}
