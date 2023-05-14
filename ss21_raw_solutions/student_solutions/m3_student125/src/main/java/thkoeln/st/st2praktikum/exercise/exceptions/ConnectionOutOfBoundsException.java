package thkoeln.st.st2praktikum.exercise.exceptions;

public class ConnectionOutOfBoundsException extends RuntimeException
{
    public ConnectionOutOfBoundsException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
