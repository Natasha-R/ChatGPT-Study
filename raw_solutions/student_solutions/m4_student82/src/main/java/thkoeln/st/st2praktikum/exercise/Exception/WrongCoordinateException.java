package thkoeln.st.st2praktikum.exercise.Exception;

public class WrongCoordinateException extends RuntimeException {
    public WrongCoordinateException(String message){
        super(message);
    }
}
