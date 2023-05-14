package thkoeln.st.st2praktikum.exercise.exceptions;

public class RobotException extends RuntimeException{
    public RobotException(String errorMessage){
        super(errorMessage);
    }
}
