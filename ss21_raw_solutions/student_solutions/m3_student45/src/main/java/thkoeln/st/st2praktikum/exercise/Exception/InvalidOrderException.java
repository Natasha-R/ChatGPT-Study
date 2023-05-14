package thkoeln.st.st2praktikum.exercise.Exception;

public class InvalidOrderException extends RuntimeException{
    public InvalidOrderException(String message){
        super(message);
    }
}
