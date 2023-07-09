package thkoeln.st.st2praktikum.exercise.Exception;

public class NoConnectionException extends RuntimeException {
    public NoConnectionException(String message){
        super(message);
    }
}
