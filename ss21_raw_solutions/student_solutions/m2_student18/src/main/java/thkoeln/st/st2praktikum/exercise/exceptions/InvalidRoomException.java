package thkoeln.st.st2praktikum.exercise.exceptions;

public class InvalidRoomException extends RuntimeException{
    public InvalidRoomException(String errorMessage){
        super(errorMessage);
    }
}
