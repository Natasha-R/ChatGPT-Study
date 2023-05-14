package thkoeln.st.st2praktikum.exercise.exception;

public class InvalidOrderException extends RuntimeException
{
    public InvalidOrderException(String command)
    {
        super(command);
    }
}
