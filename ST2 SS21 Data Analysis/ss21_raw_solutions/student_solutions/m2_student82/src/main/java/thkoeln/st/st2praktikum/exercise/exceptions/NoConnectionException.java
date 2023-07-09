package thkoeln.st.st2praktikum.exercise.exceptions;

public class NoConnectionException extends RuntimeException {
    public NoConnectionException(String message){
        super(message);
    }
}
