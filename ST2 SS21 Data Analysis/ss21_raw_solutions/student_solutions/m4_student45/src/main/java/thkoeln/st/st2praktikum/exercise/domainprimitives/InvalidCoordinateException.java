package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(String message){
        super(message);
    }
}
