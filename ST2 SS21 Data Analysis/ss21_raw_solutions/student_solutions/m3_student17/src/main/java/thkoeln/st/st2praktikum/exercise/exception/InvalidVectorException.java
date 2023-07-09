package thkoeln.st.st2praktikum.exercise.exception;

public class InvalidVectorException extends RuntimeException
{
    public InvalidVectorException(String command)
    {
        super(command);
    }
}
