package thkoeln.st.st2praktikum.exercise.exceptions;

public class WrongCoordinateException extends RuntimeException {
    public WrongCoordinateException(String message){
        super(message);
    }
}
