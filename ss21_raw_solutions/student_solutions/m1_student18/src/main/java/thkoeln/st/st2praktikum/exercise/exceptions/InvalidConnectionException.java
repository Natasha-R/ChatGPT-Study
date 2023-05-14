package thkoeln.st.st2praktikum.exercise.exceptions;

public class InvalidConnectionException extends RuntimeException{
    public InvalidConnectionException(String errorMessage){
        super(errorMessage);
    }
}
