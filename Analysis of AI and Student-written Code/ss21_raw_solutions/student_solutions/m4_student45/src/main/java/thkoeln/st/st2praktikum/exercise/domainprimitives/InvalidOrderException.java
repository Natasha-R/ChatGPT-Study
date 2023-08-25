package thkoeln.st.st2praktikum.exercise.domainprimitives;

public class InvalidOrderException extends RuntimeException{
    public InvalidOrderException(String message){
        super(message);
    }
}
