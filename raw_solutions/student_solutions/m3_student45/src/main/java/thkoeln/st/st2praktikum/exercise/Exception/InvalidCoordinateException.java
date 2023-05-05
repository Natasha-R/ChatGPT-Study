package thkoeln.st.st2praktikum.exercise.Exception;

public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(String message){
        super(message);
    }
}
