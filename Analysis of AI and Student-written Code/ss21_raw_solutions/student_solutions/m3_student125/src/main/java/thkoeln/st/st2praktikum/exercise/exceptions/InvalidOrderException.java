package thkoeln.st.st2praktikum.exercise.exceptions;

public class InvalidOrderException extends RuntimeException
{
    public InvalidOrderException(String errorMessage, Throwable error)
    {
        super(errorMessage, error);
    }
}
